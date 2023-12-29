package com.example.Backend.DTO;

import com.example.Backend.Model.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {
    private long id;
    private LocalDateTime date;
    private String status;
    private long shelterId;
    private Pet pet;
}
