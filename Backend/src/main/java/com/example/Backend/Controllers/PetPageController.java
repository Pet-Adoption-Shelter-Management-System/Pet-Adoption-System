package com.example.Backend.Controllers;

import com.example.Backend.DTO.UserInfo;
import com.example.Backend.Services.PetPageService;
import com.example.Backend.Services.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/petPage")
@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
public class PetPageController {

    private final Utils utils;

    private final PetPageService petPageService;

    public String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Skip "Bearer " prefix
        } else {
            throw new IllegalArgumentException("Authorization header doesn't exist or is in the wrong format");
        }
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<UserInfo> getUserInfo(@RequestHeader("Authorization") String authorizationHeader,
                                                @RequestParam("role") String role,
                                                @RequestParam("shelterName") String shelterName) {
        try {
            String token = extractToken(authorizationHeader);
            return ResponseEntity.status(HttpStatus.OK).body(utils.getUserInfo(token, role, shelterName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
