package com.example.Backend.Controller.Filter;

import com.example.Backend.Services.Filter.FilterService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "")
@RequestMapping("/api/filter")
public class FilterController <T extends Comparable<T>> {
    @Autowired
    FilterService filterService;

    @GetMapping("/employeeFilterEntity/{entity}/{criteria}/{toMeet}/{shelterName}")
    public List<T> employeeFilterEntity(@PathVariable String entity, @PathVariable String criteria,
                                @PathVariable String toMeet, @PathVariable String shelterName){
        return filterService.filterEmployee(entity,criteria,toMeet,shelterName);
    }
    @GetMapping("/customerFilterEntity/{entity}/{criteria}/{toMeet}")
    public List<T> customerFilterEntity(@PathVariable String entity, @PathVariable String criteria,
                                @PathVariable String toMeet){
        return filterService.filterCustomer(entity,criteria,toMeet);
    }

}
