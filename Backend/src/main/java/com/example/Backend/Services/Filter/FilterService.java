package com.example.Backend.Services.Filter;


import com.example.Backend.DTO.DocumentDto;
import com.example.Backend.DTO.PetDto;
import com.example.Backend.Model.Document;
import com.example.Backend.Model.Pet;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FilterService<T extends Comparable<T>> {
    @Autowired
    FilterPet filterPet;
    @Autowired
    FilterApplication filterApplication;

    public List<PetDto> filterCustomer(String entityName, String criteria, String toMeet) {
        try {
            System.out.println(entityName);
            System.out.println(criteria);
            System.out.println(toMeet);
            if (entityName.equals("pet")) {
                List <Pet> pets = filterPet.meetCriteriaCustomer(criteria, toMeet);
                return getPetDtos(pets);
            }
            else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List<PetDto> filterEmployee(String entityName, String criteria, String toMeet, String shelterName) {
        try {
            System.out.println(entityName);
            System.out.println(criteria);
            System.out.println(toMeet);
            System.out.println(shelterName);
            if (entityName.equals("pet")) {
                List <Pet> pets = filterPet.meetCriteriaEmployee(criteria, toMeet, shelterName);
                return getPetDtos(pets);
            }
            else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private List<PetDto> getPetDtos(List<Pet> pets) {
        return pets.stream().map(pet -> {
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
