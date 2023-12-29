package com.example.Backend.Controllers;

import com.example.Backend.DTO.ApplicationDetails;
import com.example.Backend.Services.ApplicationService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/create")
    public ResponseEntity<String> createApp(@RequestBody ApplicationDetails submit) {
        try {
            appService.submitApp(submit);
            return ResponseEntity.ok("Application submitted Successfully");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
