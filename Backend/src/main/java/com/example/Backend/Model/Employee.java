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
@Table(name = "employee", indexes = {
        @Index(name = "email", columnList = "email", unique = true)
})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first name")
    private String firstName;

    @Column(name = "last name")
    private String lastName;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "contact phone", nullable = false)
    private String contactPhone;

    @ManyToOne
    @JoinColumn(name = "shelterID", nullable = false)
    private Shelter shelter;

}