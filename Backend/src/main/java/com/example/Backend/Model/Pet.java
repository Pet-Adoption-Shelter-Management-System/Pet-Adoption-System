package com.example.Backend.Model;

import com.example.Backend.DTO.PetDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "isMale", nullable = false)
    private boolean isMale;

    @Column(name = "isHouseTrained", nullable = false)
    private boolean isHouseTrained;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "health status", nullable = false)
    private String healthStatus;

    @Column(name = "age", nullable = false)
    private float age;

    @Column(name = "behaviour", nullable = false)
    private String behaviour;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "species", nullable = false)
    private String species;

    @Column(name = "isSpayed", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isSpayed;

    @Column(name = "isAvailable", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "shelterID", nullable = false)
    @JsonIgnore
    private Shelter shelter;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PetVaccination> petVaccinations = new ArrayList<>();

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Document> documents = new ArrayList<>();

    public PetDto toDto() {
        return PetDto.builder()
                .id(this.getId())
                .name(this.getName())
                .male(this.isMale())
                .houseTrained(this.isHouseTrained())
                .description(this.getDescription())
                .healthStatus(this.getHealthStatus())
                .age(this.getAge())
                .behaviour(this.getBehaviour())
                .breed(this.getBreed())
                .species(this.getSpecies())
                .spayed(this.isSpayed())
                .available(this.isAvailable())
                .shelter(this.getShelter().toDto())
                .petVaccinations(this.getVaccinations())
                .build();
    }

    public List<String> getVaccinations() {
        return this.getPetVaccinations().stream()
                .map(PetVaccination::getVaccination).collect(Collectors.toList());
    }
}