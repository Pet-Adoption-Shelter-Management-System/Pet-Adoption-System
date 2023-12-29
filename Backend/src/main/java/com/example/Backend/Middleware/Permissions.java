package com.example.Backend.Middleware;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Permissions {
    public boolean checkManager(String token) {
        return true;
    }

    public boolean checkStaff(String token) {
        return true;
    }

    public boolean checkAdopter(String token) {
        return true;
    }

    public boolean checkToken(String authorizationHeader) {
        return authorizationHeader != null &&
                authorizationHeader.startsWith("Bearer ") && authorizationHeader.length() > 7;
    }

    public String extractToken(String authorizationHeader) {
        return authorizationHeader.substring(7);
    }
}
