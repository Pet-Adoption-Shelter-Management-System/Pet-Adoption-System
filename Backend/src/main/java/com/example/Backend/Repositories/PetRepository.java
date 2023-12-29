package com.example.Backend.Repositories;

import com.example.Backend.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("SELECT p FROM Pet p WHERE p.shelter.name = :shelterName")
    List<Pet> findByShelterName(@Param("shelterName") String shelterName);
}