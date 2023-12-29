package com.example.Backend.Controllers;

import com.example.Backend.DTO.AddEmployeeRequest;
import com.example.Backend.Services.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
public class EmployeeController {
    private final ManagerService managerService;

    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@RequestHeader("Authorization") String authorizationHeader, @RequestBody AddEmployeeRequest request) {
        try {
            String token = extractToken(authorizationHeader);
            managerService.addEmployee(token, request);
            return ResponseEntity.ok("Employee has been added and Email sent");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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
