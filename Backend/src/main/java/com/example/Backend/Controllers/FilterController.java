package com.example.Backend.Controllers;

import com.example.Backend.Services.Filter.FilterService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api/filter")
public class FilterController <T extends Comparable<T>> {
    @Autowired
    FilterService filterService;

    @GetMapping("/employeeFilterEntity/{entity}/{criteria}/{toMeet}/{shelterName}")
    public ResponseEntity<List<T>> employeeFilterEntity(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String entity, @PathVariable String criteria,
                                                        @PathVariable String toMeet, @PathVariable String shelterName){
        try {
            System.out.println("header:"+ authorizationHeader);
            String token = extractToken(authorizationHeader);
            System.out.println(token);
            return ResponseEntity.ok(filterService.filterEmployee(entity,criteria,toMeet,shelterName));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
    @GetMapping("/customerFilterEntity/{entity}/{criteria}/{toMeet}")
    public ResponseEntity<List<T>> customerFilterEntity(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String entity, @PathVariable String criteria,
                                @PathVariable String toMeet){
        try {
            extractToken(authorizationHeader);
            return ResponseEntity.ok(filterService.filterCustomer(entity,criteria,toMeet));
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
