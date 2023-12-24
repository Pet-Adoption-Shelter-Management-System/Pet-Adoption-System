package com.example.Backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "employeeID", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "shelterID", nullable = false)
    private Shelter shelter;

}