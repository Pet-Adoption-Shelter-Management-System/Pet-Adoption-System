package com.example.Backend.Services;

import com.example.Backend.DTO.AddEmployeeRequest;
import com.example.Backend.DTO.CreateShelterRequest;
import com.example.Backend.DTO.EmployeeDetails;
import com.example.Backend.DTO.UpdateShelterRequest;
import com.example.Backend.Model.Adopter;
import com.example.Backend.Model.Employee;
import com.example.Backend.Model.Shelter;
import com.example.Backend.Repositories.AdopterRepository;
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
public class ManagerService {
    private final ShelterRepository shelterRepository;
    private final EmployeeRepository employeeRepository;
    private final AdopterRepository adopterRepository;
    private final JwtService jwtService;
    private final EmailService emailService;

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

    public List<EmployeeDetails> getAllEmployees(String token, String shelterName) {
        String email = jwtService.extractUsername(token);
        verifyManager(email);
        Optional<Shelter> optionalShelter = shelterRepository.findByName(shelterName);
        if (optionalShelter.isPresent())
            return shelterRepository.getAllEmployees(optionalShelter.get().getId());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelter Not Found");
    }

    public void addEmployee(String token, AddEmployeeRequest request) throws Exception {
        String email = jwtService.extractUsername(token);
        verifyManager(email);
        Optional<Shelter> optionalShelter = shelterRepository.findByName(request.getShelterName());
        if (optionalShelter.isPresent()) {
            Optional<Adopter> optionalAdopter = adopterRepository.findByEmail(request.getEmail());
            if (optionalAdopter.isPresent())
                throw new ResponseStatusException(HttpStatus.CONFLICT, "This email already used by an adopter!");

            Optional<Employee> optionalEmployee = employeeRepository.findByEmail(request.getEmail());
            if (optionalEmployee.isPresent())
                handleFoundEmployee(optionalEmployee.get(), request);
            else
                handleNewEmployee(request, optionalShelter.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelter Not Found");
    }

    private void handleFoundEmployee(Employee employee, AddEmployeeRequest request) throws Exception {
        if (employee.isManager())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This email already used by a Manager!");
        if (!request.isManager())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This email already used by a staff member!");
        //promote unverified staff member to be a manager
        if (!employee.isVerified()){
            employee.setManager(true);
            employee.setShelter(null);
            employeeRepository.save(employee);
            newEmployeeVerification(employee);
        }
        //promote a verified staff member to be a manager
        employee.setManager(true);
        employee.setShelter(null);
        employeeRepository.save(employee);
        promotionNotification(employee);
    }

    private void handleNewEmployee(AddEmployeeRequest request, Shelter shelter) throws Exception {
        Employee employee = (Employee) Employee.builder()
                .email(request.getEmail())
                .isVerified(false)
                .build();
        employee.setManager(request.isManager());
        if(!request.isManager()){
            employee.setShelter(shelter);
        }
        employeeRepository.save(employee);
        newEmployeeVerification(employee);
    }

    private void promotionNotification(Employee employee) throws Exception {
        try {
            emailService.sendEmail(employee.getEmail(), "Promotion",
                    """
                            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 20px;">

                                <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);">

                                    <h2 style="color: #333333;">Promotion</h2>

                                    <p style="color: #666666;">Congratulations! Your manager has promoted you to be a manager</p>

                                </div>
                            </body>""");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void newEmployeeVerification(Employee employee) throws Exception {
        var jwtToken = jwtService.generateToken(employee);
        try {
            String verificationLink = "http://localhost:3000/verificationSignup?token=" + jwtToken + "&email=" + employee.getEmail();
            emailService.sendEmail(employee.getEmail(), "Email Verification",
                    "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 20px;\">\n" +
                            "\n" +
                            "    <div style=\"max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\">\n" +
                            "\n" +
                            "        <h2 style=\"color: #333333;\">Email Verification</h2>\n" +
                            "\n" +
                            "        <p style=\"color: #666666;\">Please click on the button below to complete register and verify your account:</p>\n" +
                            "\n" +
                            "        <a href=\"" + verificationLink + "\"style=\"display: inline-block; background-color: #4caf50; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; margin-top: 20px;\">Verify</a>\n" +
                            "\n" +
                            "    </div>\n" +
                            "</body>");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}



