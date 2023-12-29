package com.example.Backend.Middleware;
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
    private final EmployeeRepository employeeRepository;

    @Transactional
    public UserInfo getUserInfo (String token, String role) throws IllegalAccessException {
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
                System.out.println("manager");
                Employee manager = getManager(token);
                System.out.println("First name: ");
                System.out.println(manager.getFirstName());

                if (manager != null)
                {
                    System.out.println("Manager != null");
                    return UserInfo.builder().firstName(manager.getFirstName()).lastName(manager.getLastName()).build();

                }
                else {
                    throw new IllegalAccessException("Access Denied !");
                }
            }
            default -> throw new IllegalAccessException("Access Denied !");
        }
    }

    @Transactional
    public Adopter getAdopter(String token){
        String username = jwtService.extractUsername(token);
        return adopterRepository.findByEmail(username).orElse(null);
    }
    @Transactional
    public Employee getManager (String token){
        String username = jwtService.extractUsername(token);
        Optional<Employee> manager =  employeeRepository.findByEmail(username);
        return manager.orElse(null);
    }
    @Transactional
    public Employee getEmployee(String token){
        String username = jwtService.extractUsername(token);
        return employeeRepository.findByEmail(username).orElse(null);
    }
}
