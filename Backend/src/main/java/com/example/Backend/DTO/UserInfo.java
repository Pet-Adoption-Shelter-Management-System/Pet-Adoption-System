package com.example.Backend.DTO;
import lombok.*;

/**
 * DTO for {@link com.example.Backend.Model.Pet}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    String firstName;
    String lastName;
}
