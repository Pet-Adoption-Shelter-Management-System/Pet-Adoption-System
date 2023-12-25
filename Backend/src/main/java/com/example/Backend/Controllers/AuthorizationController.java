package com.example.Backend.Controllers;

import com.example.Backend.DTO.AuthResponse;
import com.example.Backend.DTO.LoginRequest;
import com.example.Backend.DTO.RegisterRequest;
import com.example.Backend.Services.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;
    @PostMapping("/adopterSignUp")
    public ResponseEntity<String> adopterSignUp(@RequestBody RegisterRequest request) {
        try {
            authorizationService.adopterSignUp(request);
            return ResponseEntity.ok("Signed up successfully and a verification email sent to you to complete sign up");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authorizationService.login(request));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(AuthResponse.builder().token(e.getMessage()).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AuthResponse.builder().token(e.getMessage()).build());
        }
    }
}
