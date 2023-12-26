package com.example.Backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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