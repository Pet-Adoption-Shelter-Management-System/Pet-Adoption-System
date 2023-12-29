package com.example.Backend.DTO;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.Backend.Model.Shelter}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShelterDto {
    String name;
    String location;
    String contactPhone;
    String contactEmail;
}