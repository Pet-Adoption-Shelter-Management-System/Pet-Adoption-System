package com.example.Backend.Model;

import com.example.Backend.DTO.ShelterDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shelter", indexes = {
        @Index(name = "name", columnList = "name", unique = true)
})
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "contact phone", nullable = false)
    private String contactPhone;

    @Column(name = "contact email", nullable = false)
    private String contactEmail;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Pet> pets = new ArrayList<>();

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Application> applications = new ArrayList<>();

    public ShelterDto toDto() {
        return ShelterDto.builder()
                .id(id)
                .name(name)
                .location(location)
                .contactPhone(contactPhone)
                .contactEmail(contactEmail)
                .build();
    }
}