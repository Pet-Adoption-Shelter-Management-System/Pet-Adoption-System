package com.example.Backend.Controller.Sort;

import com.example.Backend.Services.Sort.SortService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api/sort")
public class SortController <T extends Comparable<T>>{

    public class FilterController  {
        @Autowired
        SortService sortService;

        @GetMapping("/employeeSortEntity/{entity}/{sortBy}/{order}/{shelterName}")
        public List<T> employeeSortEntity(@PathVariable String entity,@PathVariable String sortBy, @PathVariable boolean order, @PathVariable String shelterName){
            return sortService.sortEmployee(entity, sortBy, order,shelterName);
        }
        @GetMapping("/customerSortEntity/{entity}/{sortBy}/{order}")
        public List<T> customerSortEntity(@PathVariable String entity,@PathVariable String sortBy, @PathVariable boolean order){
            return sortService.sortCustomer(entity, sortBy, order);
        }
    }
}
