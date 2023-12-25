package com.example.Backend.Services;

import com.example.Backend.Model.Adopter;
import com.example.Backend.Repositories.AdopterRepository;
import com.example.Backend.Repositories.EmployeeRepository;
import com.example.Backend.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserVerification {

    private final JwtService jwtService;
    private final AdopterRepository adopterRepository;

    public void verifyAdopter(String token) {
        String username = jwtService.extractUsername(token);
        Optional<Adopter> optionalAdopter = adopterRepository.findByEmail(username);
        if (optionalAdopter.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        Adopter adopter = optionalAdopter.get();
        if (!adopter.isVerified()) {
            adopter.setVerified(true);
            adopterRepository.save(adopter);
        }
        else {
            throw new IllegalStateException("adopter already verified");
        }
    }
}
