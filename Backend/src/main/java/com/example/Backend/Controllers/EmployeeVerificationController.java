package com.example.Backend.Controllers;

import com.example.Backend.DTO.EmployeeVerificationResponse;
import com.example.Backend.DTO.RegisterRequest;
import com.example.Backend.Services.UserVerification;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/EmployeeVerification")
@CrossOrigin(origins = "http://localhost:3000/")
public class EmployeeVerificationController {
    private final UserVerification userVerification;

    @PostMapping
    public ResponseEntity<EmployeeVerificationResponse> verifyAdmin(HttpServletRequest request, @RequestBody RegisterRequest registerRequest) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && authorizationHeader.length() > 7) {
            String token = authorizationHeader.substring(7);
            try {
                return ResponseEntity.status(HttpStatus.OK).body(userVerification.verifyEmployee(token, registerRequest));
            } catch (ResponseStatusException e) { //request email doesn't match the token
                return ResponseEntity.status(e.getStatusCode()).body(EmployeeVerificationResponse.builder().role(e.getReason()).build());
            } catch (UsernameNotFoundException e) { // user not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(EmployeeVerificationResponse.builder().role(e.getMessage()).build());
            } catch (IllegalStateException e) { // user already verified
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(EmployeeVerificationResponse.builder().role(e.getMessage()).build());
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(EmployeeVerificationResponse.builder().role("Invalid token").build());
        }
    }
}
