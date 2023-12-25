package com.example.Backend.Services.Sort;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SortService <T extends Comparable<T>>{
    @Autowired
    SortPet sortPet;
    @Autowired
    SortApplication sortApplication;

    public List<T> sortEmployee(String entity,String sortBy,boolean order,String shelterName){
        try {
            return switch (entity) {
                case "pet" -> sortPet.sortForEmployee(sortBy, order,shelterName);
//                case "app" -> customerAdapter.convert(sortApplication.sortForEmployee(sortBy, order,shelterId));
                default -> null;
            };
        }catch (Exception e){
            return null;
        }
    }
    public List<T> sortCustomer(String entity,String sortBy,boolean order){
        try {
            return switch (entity) {
                case "pet" -> sortPet.sortForCustomer(sortBy, order);
//                case "app" -> customerAdapter.convert(sortApplication.sortForEmployee(sortBy, order,shelterId));
                default -> null;
            };
        }catch (Exception e){
            return null;
        }
    }
}
