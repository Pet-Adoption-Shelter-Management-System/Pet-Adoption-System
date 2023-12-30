package com.example.Backend.DTO;

import com.example.Backend.Model.Application;
import lombok.*;



/**
 * DTO for {@link Application}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequestDto {
    private long petID;
    private long shelterID;
}
