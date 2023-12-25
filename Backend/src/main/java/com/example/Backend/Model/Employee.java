package com.example.Backend.Model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee", indexes = {
        @Index(name = "email", columnList = "email", unique = true)
})
public class Employee extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "isManager", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isManager;

    @ManyToOne
    @JoinColumn(name = "shelterID")
    private Shelter shelter;
}