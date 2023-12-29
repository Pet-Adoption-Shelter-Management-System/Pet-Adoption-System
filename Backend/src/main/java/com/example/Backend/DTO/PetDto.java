package com.example.Backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for {@link com.example.Backend.Model.Pet}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetDto {
    private long id;
    private String name;
    private boolean male;
    private boolean houseTrained;
    private String description;
    private String healthStatus;
    private float age;
    private String behaviour;
    private String breed;
    private String species;
    private boolean spayed;
    private boolean available;
    private ShelterDto shelter;
    private List<String> petVaccinations;
    private List<DocumentDto> docs;
}