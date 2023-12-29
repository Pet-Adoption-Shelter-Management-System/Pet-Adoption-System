package com.example.Backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetVaccinationId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "petID", nullable = false)
    private Pet pet;

    @Column(name = "vaccination", nullable = false)
    private String vaccination;
}