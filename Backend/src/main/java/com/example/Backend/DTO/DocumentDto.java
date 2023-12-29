package com.example.Backend.DTO;

import com.example.Backend.Model.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Document}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {
    private String docName;
    private String type;
    private byte[] encodedFile;
}