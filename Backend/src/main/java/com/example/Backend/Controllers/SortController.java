package com.example.Backend.Controllers;

import com.example.Backend.DTO.PetDto;
import com.example.Backend.Services.Sort.SortService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api/sort")
public class SortController <T extends Comparable<T>>{
    @Autowired
    SortService sortService;

    @GetMapping("/employeeSortEntity/{entity}/{sortBy}/{sortOrder}/{shelterName}")
    public ResponseEntity<List<PetDto>> employeeSortEntity(@RequestHeader("Authorization") String authorizationHeader,
                                                           @PathVariable String entity,
                                                           @PathVariable String sortBy,
                                                           @PathVariable boolean sortOrder,
                                                           @PathVariable String shelterName){
        try {
            System.out.println("sorting");
            System.out.println(entity);
            System.out.println(sortBy);
            System.out.println(sortOrder);
            System.out.println(shelterName);
            extractToken(authorizationHeader);
            return ResponseEntity.ok(sortService.sortEmployee(entity, sortBy, sortOrder,shelterName));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
    @GetMapping("/customerSortEntity/{entity}/{sortBy}/{sortOrder}")
    public ResponseEntity<List<PetDto>> customerSortEntity(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String entity,@PathVariable String sortBy, @PathVariable boolean sortOrder){
        try {
            System.out.println("sorting");
            System.out.println(entity);
            System.out.println(sortBy);
            System.out.println(sortOrder);
            extractToken(authorizationHeader);
            return ResponseEntity.ok(sortService.sortCustomer(entity, sortBy, sortOrder));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    public String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Skip "Bearer " prefix
        } else {
            throw new IllegalArgumentException("Authorization header doesn't exist or is in the wrong format");
        }
    }

}
