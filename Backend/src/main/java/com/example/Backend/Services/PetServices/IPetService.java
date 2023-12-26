package com.example.Backend.Services.PetServices;

import com.example.Backend.DTO.PetDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

public interface IPetService {
    void processPet(PetDto petDto, MultipartFile[] docs) throws IOException, NoSuchElementException;
    String getSuccessMessage();
}
