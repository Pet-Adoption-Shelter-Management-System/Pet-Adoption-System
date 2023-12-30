package com.example.Backend.Controllers.PetControllers;

import com.example.Backend.Middleware.Permissions;
import com.example.Backend.Middleware.Utils;
import com.example.Backend.Services.PetServices.EditPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pet/edit")
@CrossOrigin(origins = "http://localhost:3000/")
public class EditPetController {

    @Autowired
    private EditPetService editPetService;
    @Autowired
    private Permissions permissions;
    @Autowired
    private Utils utils;

    @PostMapping
    public ResponseEntity<String> editPet(@RequestParam(value = "petDto") String jsonString,
                                          @RequestParam("docs") MultipartFile[] docs,
                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        PetProcessor petProcessor = new PetProcessor(permissions, editPetService, utils);
        return petProcessor.processPet(jsonString, docs, authorizationHeader);
    }
}