package com.example.Backend.Controllers;

import com.example.Backend.DTO.PetDto;
import com.example.Backend.Services.PetDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/petDetails")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PetDetailsController {

    private final PetDetailsService petDetailsService;

    @GetMapping
    public PetDto getPetDetails(@RequestParam("petID") long petID) throws IOException {
        return petDetailsService.getPetDetails(petID);
    }
}
