package com.example.Backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
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

}