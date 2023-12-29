package com.example.Backend.Services;

import com.example.Backend.DTO.LoginRequest;
import com.example.Backend.DTO.AuthResponse;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorizationService {
    private final AdopterRepository adopterRepository;
    private final EmployeeRepository employeeRepository;
    private final ShelterRepository shelterRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    public void adopterSignUp(RegisterRequest request) throws Exception {
        if (shelterRepository.count() > 0) {
            System.out.println("count: " + shelterRepository.count());
            Optional<Adopter> optionalAdopter = adopterRepository.findByEmail(request.getEmail());
            Optional<Employee> optionalEmployee = employeeRepository.findByEmail(request.getEmail());
            if (optionalEmployee.isPresent())
                throw new ResponseStatusException(HttpStatus.CONFLICT, "This email already used by an employee!");
            Adopter adopter;
            if (optionalAdopter.isPresent()) {
                adopter = optionalAdopter.get();
                if (adopter.isVerified())
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Already exist!");
                adopter.setFirstName(request.getFirstName());
                adopter.setLastName(request.getLastName());
                adopter.setPassword(passwordEncoder.encode(request.getPassword()));
            } else {
                adopter = new Adopter();
                adopter.setEmail(request.getEmail());
                adopter.setPassword(passwordEncoder.encode(request.getPassword()));
                adopter.setFirstName(request.getFirstName());
                adopter.setLastName(request.getLastName());
                adopter.setVerified(false);
            }
            adopterRepository.save(adopter);
            System.out.println("before send");
            setupVerification(adopter);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No any shelter's created yet!");
        }
    }

    public AuthResponse login(LoginRequest request) {
        try {
            System.out.println(request);
            System.out.println("before auth");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "you are unauthorized");
        }

        System.out.println("auth manager is ok");
        Optional<Adopter> optionalAdopter = adopterRepository.findByEmail(request.getEmail());
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(request.getEmail());
        if (optionalAdopter.isPresent() && request.getRole().equals("adopter")) {
            Adopter adopter = optionalAdopter.get();
            System.out.println("is verfied" + adopter.isVerified());
            if (adopter.isVerified()) {
                var jwtToken = jwtService.generateToken(adopter);
                return AuthResponse.builder()
                        .token(jwtToken)
                        .build();
            }
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "you are unverified");
        }else if(optionalEmployee.isPresent()){
            System.out.println("found in emp table");
            return employeeLogin(optionalEmployee.get(), request);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with the specified role");
    }

    private AuthResponse employeeLogin(Employee employee, LoginRequest request){
        if(employee.isVerified()){
            if(employee.isManager() && request.getRole().equals("manager")) {
                System.out.println("he is a manager");
                Optional<Shelter> optionalShelter = shelterRepository.findByName(request.getShelterName());
                if(optionalShelter.isPresent()){
                    var jwtToken = jwtService.generateToken(employee);
                    System.out.println("shelter found");
                    return AuthResponse.builder()
                            .token(jwtToken)
                            .shelterName(optionalShelter.get().getName())
                            .build();
                }
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelter Not Found");
            }else if(!employee.isManager() && request.getRole().equals("staff")){
                var jwtToken = jwtService.generateToken(employee);
                return AuthResponse.builder()
                        .token(jwtToken)
                        .shelterName(employee.getShelter().getName())
                        .build();
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with the specified role");
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "you are unverified");
    }

    private void setupVerification(Adopter adopter) throws Exception {
        var jwtToken = jwtService.generateToken(adopter);
        try {
            emailService.sendEmail(adopter.getEmail(), "Email Verification",
                    "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 20px;\">\n" +
                            "\n" +
                            "    <div style=\"max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\">\n" +
                            "\n" +
                            "        <h2 style=\"color: #333333;\">Email Verification</h2>\n" +
                            "\n" +
                            "        <p style=\"color: #666666;\">Please click on the button below to verify your account:</p>\n" +
                            "\n" +
                            "        <a href=\"http://localhost:3000/verification?token=" + jwtToken + "\"style=\"display: inline-block; background-color: #4caf50; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; margin-top: 20px;\">Verify</a>\n" +
                            "\n" +
                            "    </div>\n" +
                            "</body>");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
