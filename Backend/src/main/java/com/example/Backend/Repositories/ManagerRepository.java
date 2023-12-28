package com.example.Backend.Repositories;

import com.example.Backend.Model.Employee;
import com.example.Backend.Model.Manager;
import com.example.Backend.Model.ManagerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, ManagerId> {
    Optional<Manager> findByEmployee_Shelter_Name(String shelterName);
}