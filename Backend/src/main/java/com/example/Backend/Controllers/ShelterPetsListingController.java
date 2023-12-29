package com.example.Backend.Controllers;

import com.example.Backend.DTO.PetDto;
import com.example.Backend.Middleware.Permissions;
import com.example.Backend.Services.PetsListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelter/allPets")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ShelterPetsListingController {

    private final PetsListingService petsListingService;
    private final Permissions permissions;

    @GetMapping
    public ResponseEntity<List<PetDto>> getAllPets(
            @RequestParam String shelterName,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        if (!permissions.checkToken(authorizationHeader)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(200).body(petsListingService.getAllPets(shelterName));
    }
}
