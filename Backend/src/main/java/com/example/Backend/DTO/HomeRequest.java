package com.example.Backend.DTO;


import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.Backend.Model.Employee}
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeRequest {
    String role;
    String shelterName;
}
