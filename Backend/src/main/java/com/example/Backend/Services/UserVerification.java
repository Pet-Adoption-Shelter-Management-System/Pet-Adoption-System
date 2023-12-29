package com.example.Backend.Services;

import com.example.Backend.DTO.EmployeeVerificationResponse;
import com.example.Backend.DTO.RegisterRequest;
import com.example.Backend.Model.Adopter;
import com.example.Backend.Model.Employee;
import com.example.Backend.Model.Shelter;
import com.example.Backend.Repositories.AdopterRepository;
import com.example.Backend.Repositories.EmployeeRepository;
import com.example.Backend.Repositories.ShelterRepository;
import com.example.Backend.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserVerification {

    private final JwtService jwtService;
    private final AdopterRepository adopterRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShelterRepository shelterRepository;

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
        } else {
            throw new IllegalStateException("adopter already verified");
        }
    }

    public EmployeeVerificationResponse verifyEmployee(String token, RegisterRequest request) {
        String email = jwtService.extractUsername(token);
        if (!email.equals(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The provided form mail doesn't match the verification mail sent");
        }
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if (optionalEmployee.isEmpty()) {
            throw new UsernameNotFoundException("Employee not found");
        }
        Employee employee = optionalEmployee.get();
        if (!employee.isVerified()) {
            employee.setVerified(true);
            employee.setPassword(passwordEncoder.encode(request.getPassword()));
            employee.setFirstName(request.getFirstName());
            employee.setLastName(request.getLastName());
            employeeRepository.save(employee);
            //return the role
            if(employee.isManager())
                return EmployeeVerificationResponse.builder()
                        .role("manager")
                        .shelterName(employee.getShelter().getName())
                        .build();

            else return EmployeeVerificationResponse.builder()
                    .role("staff")
                    .shelterName(employee.getShelter().getName())
                    .build();
        } else {
            throw new IllegalStateException("Admin already verified");
        }
    }
}