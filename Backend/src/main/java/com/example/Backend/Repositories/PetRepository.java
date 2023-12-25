package com.example.Backend.Repositories;

import com.example.Backend.Model.Pet;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository<T extends Comparable<T>> extends JpaRepository<Pet, Long> {
    //Filter
    //species
    List<Pet> findBySpeciesName(String species);
    @Query("SELECT p FROM Pet p WHERE p.species = :species AND p.shelterId = :shelterId")
    List<Pet> findBySpeciesNameAndShelter(@Param("species") String species, @Param("shelterId") long shelterId);
    //----------------------------------------------------------------------------------------------------------

    //breed
    List<Pet> findByBreed(String breed);
    @Query("SELECT p FROM Pet p WHERE p.breed = :breed AND p.shelterId = :shelterId")
    List<Pet> findByBreedAndShelter(@Param("breed") String breed, @Param("shelterId") long shelterId);
    //-----------------------------------------------------------------------------------------

    //age
    List<Pet> findByAge(float age);
    @Query("SELECT p FROM Pet p WHERE p.age = :age AND p.shelter.id = :shelterId")
    List<Pet> findByAgeAndShelter(@Param("age") float age, @Param("shelterId") long shelterId);
    //-------------------------------------------------------------------------------------------

    //shelter location
    @Query("SELECT p.* FROM pet p JOIN shelter s ON p.shelterID = s.id WHERE s.location = :location")
    List<Pet> findByShelterLocation(@Param("location") String location);
    @Query("SELECT p.* FROM pet p JOIN shelter s ON p.shelterID = s.id WHERE s.location = :location AND s.id = :shelterId")
    List<Pet> findByShelterLocationAndShelter(@Param("location") String location, @Param("shelterId") long shelterId);
    //-------------------------------------------------------------------------------------------

    //Sort
    //customer
    List<Pet> findAllByCustomer(Sort sort);
    List<Pet> findByVaccinationOrderByNameAsc();//incomplete
    List<Pet> findByVaccinationOrderByNameDesc();//incomplete
    //-------------------------------------------------------------------------------------------
    //Employee

    List<Pet> findPetsByHouseTrainAndShelterOrderedByNameAsc(@Param(("shelterId")) long shelterId);//incomplete
    List<Pet> findPetsByHouseTrainAndShelterOrderedByNameDesc(@Param(("shelterId")) long shelterId);//incomplete
    List<Pet> findPetsByStatusAndShelterOrderedByNameAsc(@Param(("shelterId")) long shelterId);//incomplete
    List<Pet> findPetsByStatusAndShelterOrderedByNameDesc(@Param(("shelterId")) long shelterId);//incomplete
    List<Pet> findPetsByVaccineAndShelterOrderedByNameAsc(@Param(("shelterId")) long shelterId);//incomplete
    List<Pet> findPetsByVaccineAndShelterOrderedByNameDesc(@Param(("shelterId")) long shelterId);//incomplete

}