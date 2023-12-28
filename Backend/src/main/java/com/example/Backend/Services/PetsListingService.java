package com.example.Backend.Services;

import com.example.Backend.DTO.DocumentDto;
import com.example.Backend.DTO.PetDto;
import com.example.Backend.Model.Document;
import com.example.Backend.Model.Pet;
import com.example.Backend.Model.PetVaccination;
import com.example.Backend.Repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetsListingService {

    private final PetRepository petRepository;

    public List<PetDto> getAllPets() {
        return petRepository.findAll().stream().map(pet -> {
            try {
                return PetDto.builder()
                        .id(pet.getId())
                        .name(pet.getName())
                        .male(pet.isMale())
                        .houseTrained(pet.isHouseTrained())
                        .description(pet.getDescription())
                        .healthStatus(pet.getHealthStatus())
                        .age(pet.getAge())
                        .behaviour(pet.getBehaviour())
                        .breed(pet.getBreed())
                        .species(pet.getSpecies())
                        .spayed(pet.getIsSpayed())
                        .shelterName(pet.getShelter().getName())
                        .petVaccinations(getPetVaccinations(pet))
                        .docs(getPetDocs(pet))
                        .build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    List<String> getPetVaccinations(Pet pet) {
        return pet.getPetVaccinations().stream()
                .map(PetVaccination::getVaccination).collect(Collectors.toList());
    }

    List<DocumentDto> getPetDocs(Pet pet) throws IOException {
        List<Document> docs = pet.getDocuments();
        for (Document doc : docs) {
            // check if the document is an image
            if (doc.getType().startsWith("image")) {
                // encode the image
                Path path = Paths.get(doc.getLink());
                DocumentDto dto = DocumentDto.builder()
                        .docName(path.getFileName().toString())
                        .type(doc.getType())
                        .encodedFile(Base64.getEncoder().encodeToString(Files.readAllBytes(path)).getBytes())
                        .build();
                return List.of(dto);
            }
        }
        return null;
    }
}
