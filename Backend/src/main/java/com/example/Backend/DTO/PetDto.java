package com.example.Backend.DTO;

import lombok.*;

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
    private Boolean spayed;
    private String shelterName;
    private List<String> petVaccinations;
}