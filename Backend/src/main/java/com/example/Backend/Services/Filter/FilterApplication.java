package com.example.Backend.Services.Filter;


import com.example.Backend.Model.Application;
import com.example.Backend.Repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterApplication<T extends Comparable<T>> implements IFilter {
    @Autowired
    ApplicationRepository appRepo;

    @Override
    public List<Application> meetCriteriaEmployee(String criteria, String toMeet, String shelterName) {
        return null;
    }

    @Override
    public List<T> meetCriteriaCustomer(String criteria, String toMeet) {
        return null;
    }
}
