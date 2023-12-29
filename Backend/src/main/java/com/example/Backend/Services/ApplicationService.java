package com.example.Backend.Services;

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
        Application application = Application.builder()
                .adopter(adopterRepo.findByEmail(token).get())
                .shelter(optionalShelter.get())
                .pet(optionalPet.get())
                .status("Pending")
                .date(java.time.LocalDateTime.now())
                .build();
        applicationRepo.save(application);
    }
}