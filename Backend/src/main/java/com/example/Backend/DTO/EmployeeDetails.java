package com.example.Backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.example.Backend.Model.Employee}
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetails implements Serializable {
    String email;
    String firstName;
    String lastName;
    String contactPhone;
    String address;
    boolean isVerified;
}