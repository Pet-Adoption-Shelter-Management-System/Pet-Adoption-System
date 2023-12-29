package com.example.Backend.Services;

import com.example.Backend.DTO.ApplicationResponse;
import com.example.Backend.Model.Adopter;
import com.example.Backend.Repositories.AdopterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdopterService {
    private final AdopterRepository adopterRepo;

    public List<ApplicationResponse> getAdopterApps(String adopterEmail) {
        Optional<Adopter> optionalAdopter = adopterRepo.findByEmail(adopterEmail);
        if (optionalAdopter.isPresent())
            return adopterRepo.getAllApps(optionalAdopter.get().getEmail());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adopter Not Found");
    }
}
