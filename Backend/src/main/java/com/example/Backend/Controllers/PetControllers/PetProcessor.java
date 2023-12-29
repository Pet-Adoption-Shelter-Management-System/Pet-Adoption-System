package com.example.Backend.Controllers.PetControllers;

import com.example.Backend.DTO.PetDto;
import com.example.Backend.Middleware.Permissions;
import com.example.Backend.Services.PetServices.IPetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public class PetProcessor {

    private final Permissions permissions;
    private final IPetService petService;

    public PetProcessor(Permissions permissions, IPetService petService) {
        this.permissions = permissions;
        this.petService = petService;
    }

    public ResponseEntity<String> processPet(String jsonString, MultipartFile[] docs, String authorizationHeader) {
        if (permissions.checkToken(authorizationHeader)) {
            String token = permissions.extractToken(authorizationHeader);
            try {
                // verify staff
                if (!permissions.checkStaff(token)) { // unauthorized user
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
                }

                // extract petDto from jsonString
                ObjectMapper map = new ObjectMapper();
                map.registerModule(new JavaTimeModule());
                PetDto petDto = map.readValue(jsonString, PetDto.class);

                // add pet
                petService.processPet(petDto, docs);
                return ResponseEntity.status(HttpStatus.OK).body(petService.getSuccessMessage());
            } catch (Exception e) { // can not add pet
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}

