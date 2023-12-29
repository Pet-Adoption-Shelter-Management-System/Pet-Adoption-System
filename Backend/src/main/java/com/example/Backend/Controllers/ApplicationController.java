package com.example.Backend.Controllers;

import com.example.Backend.DTO.ApplicationRequestDto;
import com.example.Backend.Middleware.Permissions;
import com.example.Backend.Services.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/application")
@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService appService;
    private final Permissions permissions;

    @PostMapping("/create")
    public ResponseEntity<String> createApp(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody ApplicationRequestDto applicationRequestDto
            ) {
        System.out.println(applicationRequestDto);
        if (permissions.checkToken(authorizationHeader)) {
            try {
                String token = permissions.extractToken(authorizationHeader);
                if (!permissions.checkAdopter(token)) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
                }
                appService.submitApp(token, applicationRequestDto.getPetID(), applicationRequestDto.getShelterID());
                return ResponseEntity.status(HttpStatus.OK).body("Application submitted Successfully");
            } catch (ResponseStatusException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden access");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
}
