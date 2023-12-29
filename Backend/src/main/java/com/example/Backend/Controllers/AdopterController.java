package com.example.Backend.Controllers;


import com.example.Backend.Model.Application;
import com.example.Backend.Services.AdopterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/adopter")
@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
public class AdopterController {
    private final AdopterService adopterService;

    @GetMapping("/getApps/{adopterEmail}")
    public ResponseEntity<List<Application>> getAllApps(@RequestBody String adopterEmail) {
        try {
            return ResponseEntity.ok(adopterService.getAdopterApps(adopterEmail));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (ResponseStatusException e) {
            //status code will be not found in case the adopter not found
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
