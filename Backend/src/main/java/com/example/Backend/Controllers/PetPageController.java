package com.example.Backend.Controllers;
import com.example.Backend.DTO.HomeRequest;
import com.example.Backend.DTO.UserInfo;
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

    public String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Skip "Bearer " prefix
        } else {
            throw new IllegalArgumentException("Authorization header doesn't exist or is in the wrong format");
        }
    }

    @GetMapping ("/getUserInfo/{role}")
    public ResponseEntity<UserInfo> getUserInfo (@RequestHeader("Authorization") String authorizationHeader,
                                                 @PathVariable String role) {
        try {
            String token = extractToken(authorizationHeader);
            UserInfo userInfo = utils.getUserInfo(token, role);
            return ResponseEntity.status(HttpStatus.OK).body(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
