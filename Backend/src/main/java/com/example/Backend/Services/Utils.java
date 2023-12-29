package com.example.Backend.Services;

import com.example.Backend.DTO.UserInfo;
import com.example.Backend.Model.Adopter;
import com.example.Backend.Model.Employee;
import com.example.Backend.Model.Manager;
import com.example.Backend.Repositories.AdopterRepository;
import com.example.Backend.Repositories.EmployeeRepository;
import com.example.Backend.Repositories.ManagerRepository;
import com.example.Backend.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Utils {
    private final JwtService jwtService;
    private final AdopterRepository adopterRepository;
    private final ManagerRepository managerRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public UserInfo getUserInfo(String token, String role, String shelterName) throws IllegalAccessException {
        switch (role) {
            case "adopter" -> {
                Adopter adopter = getAdopter(token);
                if (adopter != null)
                    return UserInfo.builder().firstName(adopter.getFirstName()).lastName(adopter.getLastName()).build();
                else throw new IllegalAccessException("Access Denied !");
            }
            case "staff" -> {
                Employee employee = getEmployee(token);
                if (employee != null)
                    return UserInfo.builder().firstName(employee.getFirstName()).lastName(employee.getLastName()).build();
                else throw new IllegalAccessException("Access Denied !");
            }
            case "manager" -> {
                Manager manager = getManager(token, shelterName);
                if (manager != null)
                    return UserInfo.builder().firstName(manager.getEmployee().getFirstName()).lastName(manager.getEmployee().getLastName()).build();
                else throw new IllegalAccessException("Access Denied !");
            }
            default -> throw new IllegalAccessException("Access Denied !");
        }
    }

    @Transactional
    public Adopter getAdopter(String token) {
        String username = jwtService.extractUsername(token);
        return adopterRepository.findByEmail(username).orElse(null);
    }

    @Transactional
    public Manager getManager(String token, String shelterName) {
        String username = jwtService.extractUsername(token);
        Optional<Manager> manager = managerRepository.findByEmployee_Shelter_Name(shelterName);
        return manager.orElse(null);
    }

    @Transactional
    public Employee getEmployee(String token) {
        String username = jwtService.extractUsername(token);
        return employeeRepository.findByEmail(username).orElse(null);
    }
}
