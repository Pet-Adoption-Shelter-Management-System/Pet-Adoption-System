package com.example.Backend.Controllers;

import com.example.Backend.DTO.ProfileDto;
import com.example.Backend.DTO.ShelterDto;
import com.example.Backend.DTO.UpdateShelterRequest;
import com.example.Backend.Services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/getProfile")
    public ResponseEntity<ProfileDto> getProfile(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            System.out.println("let's get token");
            String token = extractToken(authorizationHeader);
            return ResponseEntity.ok(profileService.getProfile(token));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ProfileDto.builder().email(e.getMessage()).build());
        } catch (ResponseStatusException e) {
            //status code will be not found in case the manager not found or the shelter not found
            //status code will be Forbidden if the employee is not a manager
            return ResponseEntity.status(e.getStatusCode()).body(ProfileDto.builder().email(e.getReason()).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ProfileDto.builder().email(e.getMessage()).build());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestHeader("Authorization") String authorizationHeader,
                                         @RequestBody ProfileDto request) {
        try {
            String token = extractToken(authorizationHeader);
            profileService.updateProfile(token, request);
            return ResponseEntity.ok("Profile updated Successfully");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    public String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Skip "Bearer " prefix
        } else {
            throw new IllegalArgumentException("Authorization header doesn't exist or is in the wrong format");
        }
    }
}
