package com.example.Backend.Controllers.PetControllers;

import com.example.Backend.Middleware.Permissions;
import com.example.Backend.Services.PetServices.AddPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pet/add")
@CrossOrigin(origins = "http://localhost:3000/")
public class AddPetController {

    @Autowired
    private AddPetService addPetService;
    @Autowired
    private Permissions permissions;

    @PostMapping
    public ResponseEntity<String> addPet(@RequestParam(value = "petDto") String jsonString,
                                         @RequestParam("docs") MultipartFile[] docs,
                                         @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        PetProcessor petProcessor = new PetProcessor(permissions, addPetService);
        return petProcessor.processPet(jsonString, docs, authorizationHeader);
    }
}
