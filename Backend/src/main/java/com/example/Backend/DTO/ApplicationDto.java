package com.example.Backend.DTO;

import com.example.Backend.Model.Application;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Application}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto {
    private long id;
    private long adopterID;
    private long shelterID;
    private long petID;
    private LocalDateTime date;
    private String status;
}