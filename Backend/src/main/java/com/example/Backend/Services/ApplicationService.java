package com.example.Backend.Services;

import com.example.Backend.Model.Application;
import com.example.Backend.Repositories.ShelterRepository;
import com.example.Backend.Repositories.AdopterRepository;
import com.example.Backend.Repositories.PetRepository;
import com.example.Backend.DTO.ApplicationDetails;
import com.example.Backend.Repositories.ApplicationRepository;
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

    public void submitApp(ApplicationDetails applicationDetails){
        Optional<Application> optionalApp = applicationRepo.findById(applicationDetails.getId());
        if(optionalApp.isEmpty()){
            Application application = Application.builder()
                    .id(applicationDetails.getId())
                    .date(applicationDetails.getDateTime())
                    .status(applicationDetails.getStatus())
                    .adopter(adopterRepo.findById(applicationDetails.getAdopterID()).get())
                    .shelter(shelterRepo.findById(applicationDetails.getShelterID()).get())
                    .pet(petRepo.findById(applicationDetails.getPetID()).get())
                    .build();
            applicationRepo.save(application);
        }else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Application already exists");
        }
    }
}
