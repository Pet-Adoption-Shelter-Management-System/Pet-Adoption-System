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
public class DocumentId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "petID", nullable = false)
    private Pet pet;

    @Column(name = "link", nullable = false)
    private String link;

}