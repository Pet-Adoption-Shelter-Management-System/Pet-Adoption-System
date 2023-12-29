package com.example.Backend.Services.Filter;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FilterService<T extends Comparable<T>> {
    @Autowired
    FilterPet filterPet;
    @Autowired
    FilterApplication filterApplication;

    public List<T> filterCustomer(String entityName, String criteria, String toMeet) {
        try {
            System.out.println(entityName);
            System.out.println(criteria);
            System.out.println(toMeet);
            return switch (entityName) {
                case "pet" -> filterPet.meetCriteriaCustomer(criteria, toMeet);
                case "app" -> filterApplication.meetCriteriaCustomer(criteria, toMeet);
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }

    public List<T> filterEmployee(String entityName, String criteria, String toMeet, String shelterName) {
        try {
            System.out.println(entityName);
            System.out.println(criteria);
            System.out.println(toMeet);
            System.out.println(shelterName);
            return switch (entityName) {
                case "pet" -> filterPet.meetCriteriaEmployee(criteria, toMeet, shelterName);
                case "app" -> filterApplication.meetCriteriaEmployee(criteria, toMeet, shelterName);
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }
}
