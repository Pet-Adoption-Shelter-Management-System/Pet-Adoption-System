package com.example.Backend.DTO;

import com.example.Backend.Model.User;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    String email;
    String firstName;
    String lastName;
    String contactPhone;
    String address;
}