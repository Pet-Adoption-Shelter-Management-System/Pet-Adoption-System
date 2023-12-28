package com.example.Backend.config;

import com.example.Backend.Model.Adopter;
import com.example.Backend.Model.Employee;
import com.example.Backend.Repositories.AdopterRepository;
import com.example.Backend.Repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final EmployeeRepository employeeRepository;
    private final AdopterRepository adopterRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<Adopter> optionalAdopter = adopterRepository.findByEmail(username);
            Optional<Employee> optionalEmployee = employeeRepository.findByEmail(username);
            if (optionalAdopter.isPresent()) {
                return optionalAdopter.get();
            } else if (optionalEmployee.isPresent()) {
                return optionalEmployee.get();
            } else {
                throw new UsernameNotFoundException("User Email Not Found");
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}