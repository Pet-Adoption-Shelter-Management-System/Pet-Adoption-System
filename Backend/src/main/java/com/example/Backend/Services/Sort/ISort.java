package com.example.Backend.Services.Sort;

import java.util.List;

public interface ISort <T extends Comparable<T>>{
    List<T> sortForEmployee(String sortBy, boolean order, long shelterId);
    List<T> sortForCustomer(String sortBy, boolean order);
}
