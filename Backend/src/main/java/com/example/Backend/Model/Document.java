package com.example.Backend.Model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "document")
@IdClass(DocumentId.class)
public class Document {
    @Id
    @ManyToOne
    @JoinColumn(name = "petID", nullable = false)
    private Pet pet;

    @Id
    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "type", nullable = false)
    private String type;
}