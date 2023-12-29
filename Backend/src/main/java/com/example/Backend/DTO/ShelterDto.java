package com.example.Backend.DTO;

import com.example.Backend.Model.Shelter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Shelter}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShelterDto {
    long id;
    String name;
    String location;
    String contactPhone;
    String contactEmail;
}