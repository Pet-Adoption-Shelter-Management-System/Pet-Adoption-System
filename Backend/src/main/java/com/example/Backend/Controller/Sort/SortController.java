package com.example.Backend.Controller.Sort;

import com.example.Backend.Services.Sort.SortService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "")
@RequestMapping("/api/sort")
public class SortController <T extends Comparable<T>>{

    public class FilterController  {
        @Autowired
        SortService sortService;

        @GetMapping("/employeeSortEntity/{entity}/{sortBy}/{order}/{shelterId}")
        public List<T> employeeSortEntity(@PathVariable String entity,@PathVariable String sortBy, @PathVariable boolean order, @PathVariable long shelterId){
            return sortService.sortEmployee(entity, sortBy, order,shelterId);
        }
        @GetMapping("/customerSortEntity/{entity}/{sortBy}/{order}")
        public List<T> customerSortEntity(@PathVariable String entity,@PathVariable String sortBy, @PathVariable boolean order){
            return sortService.sortCustomer(entity, sortBy, order);
        }
    }
}
