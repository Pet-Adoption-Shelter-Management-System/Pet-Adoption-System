package com.example.Backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppManageDto {
    private long appId;
    private String status;
}
