package com.example.Backend.Services;

import com.example.Backend.DTO.DocumentDto;
import com.example.Backend.DTO.PetDto;
import com.example.Backend.Model.Document;
import com.example.Backend.Model.Pet;
import com.example.Backend.Repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetsListingService {

    private final PetRepository petRepository;

    public List<PetDto> getAllPets() {
        return petRepository.findAll().stream().map(pet -> {
            try {
                PetDto dto = pet.toDto();
                dto.setDocs(getPetDocs(pet));
                return dto;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    public List<PetDto> getAllPets(String shelterName) {
        return petRepository.findByShelterName(shelterName).stream().map(pet -> {
            try {
                PetDto dto = pet.toDto();
                dto.setDocs(getPetDocs(pet));
                return dto;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    List<DocumentDto> getPetDocs(Pet pet) throws IOException {
        List<Document> docs = pet.getDocuments();
        for (Document doc : docs) {
            // check if the document is an image
            if (doc.getType().startsWith("image")) {
                // encode the image
                Path path = Paths.get(new File(".").getCanonicalPath() + doc.getLink());
                DocumentDto dto = DocumentDto.builder()
                        .docName(path.getFileName().toString())
                        .type(doc.getType())
                        .encodedFile(Files.readAllBytes(path))
                        .build();
                return List.of(dto);
            }
        }
        return null;
    }
}
