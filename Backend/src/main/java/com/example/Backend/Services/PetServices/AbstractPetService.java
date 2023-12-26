package com.example.Backend.Services.PetServices;

import com.example.Backend.DTO.PetDto;
import com.example.Backend.Model.Document;
import com.example.Backend.Model.Pet;
import com.example.Backend.Model.PetVaccination;
import com.example.Backend.Model.Shelter;
import com.example.Backend.Repositories.DocumentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class AbstractPetService implements IPetService {

    protected void setPet(PetDto petDto, Pet pet, Shelter shelter) {
        pet.setName(petDto.getName());
        pet.setMale(petDto.isMale());
        pet.setHouseTrained(petDto.isHouseTrained());
        pet.setDescription(petDto.getDescription());
        pet.setHealthStatus(petDto.getHealthStatus());
        pet.setAge(petDto.getAge());
        pet.setBehaviour(petDto.getBehaviour());
        pet.setBreed(petDto.getBreed());
        pet.setSpecies(petDto.getSpecies());
        pet.setIsSpayed(petDto.getSpayed());
        pet.setShelter(shelter);
    }

    protected void setDocs(String[][] docsLinks, Pet pet) {
        List<Document> documents = new ArrayList<>(docsLinks.length);
        for (String[] linkContent : docsLinks) {
            Document document = new Document(pet, linkContent[0], linkContent[1]);
            documents.add(document);
        }
        pet.setDocuments(documents);
//        pet.setDocuments();
//        documentRepository.saveAll(Arrays.stream(docsLinks)
//                .map(linkContent -> new Document(pet, linkContent[0], linkContent[1]))
//                .collect(Collectors.toList()));
//        List<Document> documents = Arrays.stream(docsLinks)
//                .map(linkContent -> new Document(pet, linkContent[0], linkContent[1]))
//                .toList();
//        System.out.println(documents);
//        pet.setDocuments(documents);

//        pet.getDocuments().addAll(documents);
//        pet.setDocuments(pet.getDocuments());
    }

    protected void setVaccinations(List<String> vaccinations, Pet pet) {
//        List<PetVaccination> petVaccinations = vaccinations.stream()
//                .map(vaccination -> new PetVaccination(pet, vaccination))
//                .toList();
//        return petVaccinations;
////        pet.setPetVaccinations(petVaccinations);
        List<PetVaccination> petVaccinations = new ArrayList<>();
        for (String vaccination : vaccinations) {
            PetVaccination petVaccination = new PetVaccination(pet, vaccination);
            petVaccinations.add(petVaccination);
        }
        pet.setPetVaccinations(petVaccinations);
    }
}
