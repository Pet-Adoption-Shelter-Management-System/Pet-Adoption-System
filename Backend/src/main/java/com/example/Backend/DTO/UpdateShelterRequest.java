package com.example.Backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShelterRequest {
    private String oldName;
    private String shelterName;
    private String address;
    private String contactPhone;
    private String contactEmail;
}
