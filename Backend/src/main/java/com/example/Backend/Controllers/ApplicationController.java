package com.example.Backend.Controllers;

import com.example.Backend.DTO.AppManageDto;
import com.example.Backend.DTO.ApplicationDto;
import com.example.Backend.DTO.ApplicationRequestDto;
import com.example.Backend.Middleware.Permissions;
import com.example.Backend.Services.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @PostMapping("/manage")
    public ResponseEntity<String> manageApp(
            @RequestBody AppManageDto appManageDto,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        if (permissions.checkToken(authorizationHeader)) {
            try {
                String token = permissions.extractToken(authorizationHeader);
                if (!permissions.checkStaff(token)) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
                }
                appService.manageApp(appManageDto.getAppId(), appManageDto.getStatus());
                return ResponseEntity.status(HttpStatus.OK).body("Status updated");
            } catch (ResponseStatusException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden access");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

    @GetMapping("/get")
    public ResponseEntity<List<ApplicationDto>> getApps(
            @RequestParam(required = false) String shelterName,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        if (permissions.checkToken(authorizationHeader)) {
            try {
                String token = permissions.extractToken(authorizationHeader);
                if (permissions.checkAdopter(token)) {
                    return ResponseEntity.status(HttpStatus.OK).body(appService.getAppsByToken(token));
                }
                if (permissions.checkStaff(token)) {
                    return ResponseEntity.status(HttpStatus.OK).body(appService.getAppsByShelter(shelterName));
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } catch (ResponseStatusException e) {
                return ResponseEntity.status(e.getStatusCode()).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
