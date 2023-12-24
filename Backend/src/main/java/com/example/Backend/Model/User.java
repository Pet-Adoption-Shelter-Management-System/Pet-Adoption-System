package com.example.Backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first name", nullable = false)
    private String firstName;

    @Column(name = "last name", nullable = false)
    private String lastName;

    @Column(name = "contact phone")
    private String contactPhone;

    @Column(name = "address")
    private String address;
}
