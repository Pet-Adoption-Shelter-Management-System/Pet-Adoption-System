package com.example.Backend.Services;

import com.example.Backend.DTO.CreateShelterRequest;
import com.example.Backend.DTO.EmployeeDetails;
import com.example.Backend.DTO.UpdateShelterRequest;
import com.example.Backend.Model.Employee;
import com.example.Backend.Model.Shelter;
import com.example.Backend.Repositories.EmployeeRepository;
import com.example.Backend.Repositories.ShelterRepository;
import com.example.Backend.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShelterServices {
    private final ShelterRepository shelterRepository;
    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;

    public void verifyManager(String email) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if (optionalEmployee.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager Not Found");
        Employee employee = optionalEmployee.get();
        if (!employee.isManager() || !employee.isVerified())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Allowed");
    }

    public void createShelter(CreateShelterRequest request) {
        verifyManager(request.getEmail());
        Optional<Shelter> optionalShelter = shelterRepository.findByName(request.getShelterName());
        if (optionalShelter.isEmpty()) {
            Shelter shelter = Shelter.builder()
                    .name(request.getShelterName())
                    .location(request.getAddress())
                    .contactEmail(request.getEmail())
                    .contactPhone(request.getContactPhone())
                    .build();
            shelterRepository.save(shelter);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Shelter already exists");
        }
    }

    public void editShelter(String token, UpdateShelterRequest request) {
        String email = jwtService.extractUsername(token);
        verifyManager(email);
        Optional<Shelter> optionalShelter = shelterRepository.findByName(request.getShelterName());
        if (optionalShelter.isPresent()) {
            Shelter shelter = optionalShelter.get();
            shelter.setLocation(request.getAddress());
            shelter.setContactEmail(request.getContactEmail());
            shelter.setContactPhone(request.getContactPhone());
            shelterRepository.save(shelter);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelter Not Found");
        }
    }

    public List<EmployeeDetails> getAllEmployees(String token, String shelterName){
        String email = jwtService.extractUsername(token);
        verifyManager(email);
        Optional<Shelter> optionalShelter = shelterRepository.findByName(shelterName);
        if (optionalShelter.isPresent())
            return shelterRepository.getAllEmployees(optionalShelter.get().getId());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelter Not Found");
    }




}



