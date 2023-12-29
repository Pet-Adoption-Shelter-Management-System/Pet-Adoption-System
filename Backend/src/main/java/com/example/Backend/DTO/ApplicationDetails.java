package com.example.Backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDetails {
    private long id;
    private LocalDateTime dateTime;
    private String status;
    private long adopterID;
    private long shelterID;
    private long petID;
}
