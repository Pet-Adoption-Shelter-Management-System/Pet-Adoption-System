package com.example.Backend.Controllers;

import com.example.Backend.DTO.PetDto;
import com.example.Backend.Middleware.Permissions;
import com.example.Backend.Services.PetDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/petDetails")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PetDetailsController {

    private final PetDetailsService petDetailsService;
    private final Permissions permissions;

    @GetMapping
    public ResponseEntity<PetDto> getPetDetails(
            @RequestParam("petID") long petID,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws IOException {
        if (!permissions.checkToken(authorizationHeader)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(200).body(petDetailsService.getPetDetails(petID));
    }
}
