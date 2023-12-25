package com.example.Backend.Services.Filter;

import java.util.List;

public interface IFilter<T extends Comparable<T>> {
    List<T> meetCriteriaEmployee(String criteria, String toMeet, long shelterId);

    List<T> meetCriteriaCustomer(String criteria, String toMeet);
}
