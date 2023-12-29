package com.example.Backend.Services;

import com.example.Backend.Middleware.Utils;
import com.example.Backend.Model.Application;
import com.example.Backend.Model.Pet;
import com.example.Backend.Model.Shelter;
import com.example.Backend.Repositories.AdopterRepository;
import com.example.Backend.Repositories.ApplicationRepository;
import com.example.Backend.Repositories.PetRepository;
import com.example.Backend.Repositories.ShelterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationService {

    private final AdopterRepository adopterRepo;
    private final PetRepository petRepo;
    private final ShelterRepository shelterRepo;
    private final Utils utils;
    private final ApplicationRepository applicationRepo;

    public void submitApp(String token, long petId, long shelterId) {
        Optional<Pet> optionalPet = petRepo.findById(petId);
        if (optionalPet.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
        }
        Optional<Shelter> optionalShelter = shelterRepo.findById(shelterId);
        if (optionalShelter.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelter not found");
        }
//         Then search if there's already an application for this pet
        Optional<Application> optionalApplication = applicationRepo.findByPet_Id(petId);
        if(optionalApplication.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There exist already an " +
                    "application for this pet");
        }
        Pet pet = optionalPet.get();
        pet.setAvailable(false);
        petRepo.save(pet);
        Application application = Application.builder()
                .adopter(utils.getAdopter(token))
                .shelter(optionalShelter.get())
                .pet(optionalPet.get())
                .status("Pending")
                .date(java.time.LocalDateTime.now())
                .build();
        applicationRepo.save(application);
    }

    public void manageApp(long appId, String status) {
        Optional<Application> optionalApp = applicationRepo.findById(appId);
        if (optionalApp.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found");
        }
        Application application = optionalApp.get();
        if (!status.equals("Approved") && !status.equals("Rejected") && !status.equals("Pending")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status");
        }
        application.setStatus(status);
        applicationRepo.save(application);
        if (status.equals("Rejected")) {
            Pet pet = application.getPet();
            pet.setAvailable(true);
            petRepo.save(pet);
        }
    }
}
