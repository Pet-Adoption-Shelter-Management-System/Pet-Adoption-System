package com.example.Backend.Controllers;

import com.example.Backend.DTO.PetDto;
import com.example.Backend.Services.PetsListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allPets")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PetListingController {

    private final PetsListingService petsListingService;
    public String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Skip "Bearer " prefix
        } else {
            throw new IllegalArgumentException("Authorization header doesn't exist or is in the wrong format");
        }
    }
    @GetMapping
    public List<PetDto> getAllPets(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = extractToken(authorizationHeader);
            return petsListingService.getAllPets();
        } catch ( Exception e){
            return null;
        }
    }
}
