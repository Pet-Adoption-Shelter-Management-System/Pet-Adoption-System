package com.example.Backend.Repositories;

import com.example.Backend.DTO.EmployeeDetails;
import com.example.Backend.Model.Employee;
import com.example.Backend.Model.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    Optional<Shelter> findByName(String name);
  
    @Query("SELECT new com.example.Backend.DTO.EmployeeDetails(e.email, e.firstName, e.lastName, e.contactPhone, e.address, e.isVerified) " +
            "FROM Shelter s JOIN s.employees e WHERE s.id = :shelterId")
    List<EmployeeDetails> getAllEmployees(Long shelterId);

    // Add this method to get the first shelter by ID in ascending order
    Optional<Shelter> findFirstByOrderByIdAsc();
}