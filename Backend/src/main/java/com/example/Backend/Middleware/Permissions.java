package com.example.Backend.Middleware;

import com.example.Backend.Model.Employee;
import com.example.Backend.Repositories.AdopterRepository;
import com.example.Backend.Repositories.EmployeeRepository;
import com.example.Backend.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Permissions {

    private final JwtService jwtService;
    private final EmployeeRepository employeeRepository;
    private final AdopterRepository adopterRepository;

    public boolean checkManager(String token) {
        Optional<Employee> optional = employeeRepository.findByEmail(jwtService.extractUsername(token));
        return optional.map(Employee::isManager).orElse(false);
    }

    public boolean checkStaff(String token) {
        return employeeRepository.findByEmail(jwtService.extractUsername(token)).isPresent();
    }

    public boolean checkAdopter(String token) {
        return adopterRepository.findByEmail(jwtService.extractUsername(token)).isPresent();
    }

    public boolean checkToken(String authorizationHeader) {
        return authorizationHeader != null &&
                authorizationHeader.startsWith("Bearer ") && authorizationHeader.length() > 7;
    }

    public String extractToken(String authorizationHeader) {
        return authorizationHeader.substring(7);
    }
}
