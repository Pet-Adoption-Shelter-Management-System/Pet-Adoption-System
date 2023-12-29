package com.example.Backend.Controllers;

import com.example.Backend.DTO.CreateShelterRequest;
import com.example.Backend.DTO.EmployeeDetails;
import com.example.Backend.DTO.ShelterDto;
import com.example.Backend.DTO.UpdateShelterRequest;
import com.example.Backend.Services.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shelter")
@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
public class ShelterController {

    private final ManagerService shelterServices;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CreateShelterRequest request) {
        try {
            shelterServices.createShelter(request);
            return ResponseEntity.ok("Shelter Created Successfully");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/getShelter/{shelterName}")
    public ResponseEntity<ShelterDto> getShelter(@RequestHeader("Authorization") String authorizationHeader,
                                                 @PathVariable String shelterName) {
        try {
            System.out.println("let's get token");
            extractToken(authorizationHeader);
            System.out.println("name:" + shelterName);
            return ResponseEntity.ok(shelterServices.getShelter(shelterName));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ShelterDto.builder().name(e.getMessage()).build());
        } catch (ResponseStatusException e) {
            //status code will be not found in case the manager not found or the shelter not found
            //status code will be Forbidden if the employee is not a manager
            return ResponseEntity.status(e.getStatusCode()).body(ShelterDto.builder().name(e.getReason()).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ShelterDto.builder().name(e.getMessage()).build());
        }
    }


    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestHeader("Authorization") String authorizationHeader,
                                         @RequestBody UpdateShelterRequest request) {
        try {
            String token = extractToken(authorizationHeader);
            shelterServices.editShelter(token, request);
            return ResponseEntity.ok("Shelter updated Successfully");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/getEmployees/{shelterName}")
    public ResponseEntity<List<EmployeeDetails>> getEmployees(@RequestHeader("Authorization") String authorizationHeader,
                                                              @PathVariable String shelterName) {
        try {
            String token = extractToken(authorizationHeader);
            return ResponseEntity.ok(shelterServices.getAllEmployees(token, shelterName));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (ResponseStatusException e) {
            //status code will be not found in case the manager not found or the shelter not found
            //status code will be Forbidden if the employee is not a manager
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
