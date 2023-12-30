package com.example.Backend.DTO;

import com.example.Backend.Model.Application;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String firstName;
    private String lastName;
    private String email;
    private String contactPhone;
    private String address;
    private PetDto petDto;
    private LocalDateTime date;
    private String status;
}