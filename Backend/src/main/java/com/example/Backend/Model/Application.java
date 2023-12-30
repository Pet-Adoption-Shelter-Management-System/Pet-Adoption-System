package com.example.Backend.Model;

import com.example.Backend.DTO.ApplicationDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adopterID", nullable = false)
    private Adopter adopter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelterID", nullable = false)
    private Shelter shelter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "petID", nullable = false)
    private Pet pet;

    public ApplicationDto toDto() {
        return ApplicationDto.builder()
                .id(id)
                .adopterID(adopter.getId())
                .firstName(adopter.getFirstName())
                .lastName(adopter.getLastName())
                .email(adopter.getEmail())
                .contactPhone(adopter.getContactPhone())
                .address(adopter.getAddress())
                .petDto(pet.toDto())
                .date(date)
                .status(status)
                .build();
    }
}