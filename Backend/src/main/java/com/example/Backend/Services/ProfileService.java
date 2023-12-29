package com.example.Backend.Services;

import com.example.Backend.DTO.ProfileDto;
import com.example.Backend.Model.Adopter;
import com.example.Backend.Model.Employee;
import com.example.Backend.Repositories.AdopterRepository;
import com.example.Backend.Repositories.EmployeeRepository;
import com.example.Backend.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {
    private final JwtService jwtService;
    private final EmployeeRepository employeeRepository;
    private final AdopterRepository adopterRepository;
    public ProfileDto getProfile(String token){
        String email = jwtService.extractUsername(token);
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if(optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            return ProfileDto.builder()
                    .email(employee.getEmail())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .contactPhone(employee.getContactPhone())
                    .address(employee.getAddress())
                    .build();
        }else{
            Optional<Adopter> optionalAdopter = adopterRepository.findByEmail(email);
            if(optionalAdopter.isPresent()){
                Adopter adopter = optionalAdopter.get();
                return ProfileDto.builder()
                        .email(adopter.getEmail())
                        .firstName(adopter.getFirstName())
                        .lastName(adopter.getLastName())
                        .contactPhone(adopter.getContactPhone())
                        .address(adopter.getAddress())
                        .build();
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
            }
        }
    }

    public void updateProfile(String token, ProfileDto newProfile){
        String email = jwtService.extractUsername(token);
        if(!email.equals(newProfile.getEmail()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Can't be changed");
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if(optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            employee.setAddress(newProfile.getAddress());
            employee.setFirstName(newProfile.getFirstName());
            employee.setLastName(newProfile.getLastName());
            employee.setContactPhone(newProfile.getContactPhone());
            employeeRepository.save(employee);
        }else{
            Optional<Adopter> optionalAdopter = adopterRepository.findByEmail(email);
            if(optionalAdopter.isPresent()){
                Adopter adopter = optionalAdopter.get();
                adopter.setAddress(newProfile.getAddress());
                adopter.setFirstName(newProfile.getFirstName());
                adopter.setLastName(newProfile.getLastName());
                adopter.setContactPhone(newProfile.getContactPhone());
                adopterRepository.save(adopter);
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
            }
        }
    }
}
