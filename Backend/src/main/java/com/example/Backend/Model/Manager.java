package com.example.Backend.Model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "manager")
@IdClass(ManagerId.class)
public class Manager {
    @Id
    @ManyToOne
    @JoinColumn(name = "employeeID", nullable = false)
    private Employee employee;

    @Id
    @ManyToOne
    @JoinColumn(name = "shelterID", nullable = false)
    private Shelter shelter;
}