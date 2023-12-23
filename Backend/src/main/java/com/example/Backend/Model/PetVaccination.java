package com.example.Backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "petvaccinations")
@IdClass(PetVaccinationId.class)
public class PetVaccination {
    @Id
    @ManyToOne
    @JoinColumn(name = "petID", nullable = false)
    private Pet pet;
    @Id
    private String vaccination;

}