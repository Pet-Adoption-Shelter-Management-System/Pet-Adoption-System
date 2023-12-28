package com.example.Backend.Controllers;

import com.example.Backend.DTO.PetDto;
import com.example.Backend.Services.PetsListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/allPets")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PetListingController {

    private final PetsListingService petsListingService;

    @GetMapping
    public List<PetDto> getAllPets() {
        return petsListingService.getAllPets();
    }
}
