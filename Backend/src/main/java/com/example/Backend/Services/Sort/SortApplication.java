package com.example.Backend.Services.Sort;

import com.example.Backend.Model.Application;
import com.example.Backend.Repositories.ApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SortApplication <T extends Comparable<T>> implements ISort {
    @Autowired
    ApplicationRepository appRepo;

    @Override
    public List<Application> sortForEmployee(String sortBy, boolean order, String shelterName) {
        return null;
    }

    @Override
    public List<Application> sortForCustomer(String sortBy, boolean order) {
        return null;
    }
}
