package com.example.Backend.Services.PetServices;

import com.example.Backend.DTO.PetDto;
import com.example.Backend.Model.Pet;
import com.example.Backend.Model.Shelter;
import com.example.Backend.Repositories.PetRepository;
import com.example.Backend.Repositories.PetVaccinationRepository;
import com.example.Backend.Repositories.ShelterRepository;
import com.example.Backend.Services.DocumentService;
import com.example.Backend.Services.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
public class AddPetService extends AbstractPetService {

    private final PetRepository petRepository;
    private final ShelterRepository shelterRepository;
    private final PetVaccinationRepository petVaccinationRepository;
    private final DocumentService documentService;
    private final NotificationService notificationService;

    @Override
    public void processPet(PetDto petDto, MultipartFile[] docs) throws IOException {
        Optional<Shelter> optionalShelter = shelterRepository.findByName(petDto.getShelter().getName());
        if (optionalShelter.isEmpty()) {
            throw new RuntimeException("Shelter does not exist");
        }
        Pet pet = new Pet();
        try {
            setPet(petDto, pet, optionalShelter.get());
            pet = petRepository.save(pet);
            petVaccinationRepository.deleteByPet(pet);
            setVaccinations(petDto.getPetVaccinations(), pet);
            setDocs(documentService.saveDocs(docs, pet.getId()), pet);
            petRepository.save(pet);
            Pet finalPet = pet;
            CompletableFuture.runAsync(() -> notificationService.notifyAllUsers(finalPet));
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public String getSuccessMessage() {
        return "Pet added successfully";
    }
}
