package com.example.Backend.Repositories;

import com.example.Backend.Model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByPet_Id(long petId);

    List<Application> findByAdopter_Id(long id);

    List<Application> findByShelter_Name(String shelterName);

}