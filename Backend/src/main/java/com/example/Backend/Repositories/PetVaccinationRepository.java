package com.example.Backend.Repositories;

import com.example.Backend.Model.Pet;
import com.example.Backend.Model.PetVaccination;
import com.example.Backend.Model.PetVaccinationId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetVaccinationRepository extends JpaRepository<PetVaccination, PetVaccinationId> {
    @Transactional
    void deleteByPet(Pet pet);
}