package com.example.Backend.Repositories;

import com.example.Backend.Model.Adopter;
import com.example.Backend.Model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdopterRepository extends JpaRepository<Adopter, Long> {
    Optional<Adopter> findByEmail(String email);

    @Query("SELECT a.applications FROM Adopter a WHERE a.id = :adopterId")
    List<Application> findApplicationsByAdopterId(Long adopterId);
}