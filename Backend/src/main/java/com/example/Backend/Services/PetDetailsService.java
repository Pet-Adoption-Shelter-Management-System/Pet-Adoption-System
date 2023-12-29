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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetDetailsService {

    private final PetRepository petRepository;

    public PetDto getPetDetails(long petID) throws IOException {
        return toDto(petRepository.findById(petID).orElseThrow());
    }

    private PetDto toDto(Pet pet) throws IOException {
        PetDto dto = pet.toDto();
        dto.setDocs(getPetDocs(pet));
        return dto;
    }

    List<DocumentDto> getPetDocs(Pet pet) throws IOException {
        List<Document> docs = pet.getDocuments();
        List<DocumentDto> documentDtoList = new ArrayList<>();
        for (Document doc : docs) {
            Path path = Paths.get(new File(".").getCanonicalPath() + doc.getLink());
            documentDtoList.add(DocumentDto.builder()
                    .docName(path.getFileName().toString())
                    .type(doc.getType())
                    .encodedFile(Files.readAllBytes(path))
                    .build());
        }
        return documentDtoList;
    }
}
