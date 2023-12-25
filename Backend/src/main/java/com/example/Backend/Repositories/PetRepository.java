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
    List<Pet> findBySpecies(String species);
    List<Pet> findBySpeciesAndShelter_Name(String species, String shelterName);
    //----------------------------------------------------------------------------------------------------------

    //breed
    List<Pet> findByBreed(String breed);
    List<Pet> findByBreedAndShelter_Name(String breed, String shelterName);
    //-----------------------------------------------------------------------------------------

    //age
    List<Pet> findByAge(float age);
    List<Pet> findByAgeAndShelter_Name(float age, String shelterName);
    //-------------------------------------------------------------------------------------------

    //shelter location
    List<Pet> findByShelterLocation(String location);
    List<Pet> findByShelterLocationAndShelter_Name(String location,String shelterName);
    List<Pet> findByPetVaccinations_Vaccination(String vaccine);
    List<Pet> findByPetVaccinations_VaccinationAndShelter_Name(String vaccine, String shelterName);
    //-------------------------------------------------------------------------------------------

    //Sort
    //customer
    List<Pet> findAllByOrderByIsHouseTrainedAsc();
    List<Pet> findAllByOrderByIsHouseTrainedDesc();
    List<Pet> findAllByOrderByIsSpayedAsc();
    List<Pet> findAllByOrderByIsSpayedDesc();
    //-------------------------------------------------------------------------------------------

    //Employee
    List<Pet> findAllByOrderByIsHouseTrainedAndShelter_NameAsc(String shelterName);
    List<Pet> findAllByOrderByIsHouseTrainedAndShelter_NameDesc(String shelterName);
    List<Pet> findAllByOrderByIsSpayedAndShelter_NameAsc(String shelterName);
    List<Pet> findAllByOrderByIsSpayedAndShelter_NameDesc(String shelterName);
    //---------------------------------------------------------------------------------------

    //search
    @Query("SELECT p FROM Pet p " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(CAST(p.isMale AS string)) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(CAST(p.isHouseTrained AS string)) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(CAST(p.age AS string)) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(p.healthStatus) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(p.behaviour) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(p.breed) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(p.species) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(CAST(p.isSpayed AS string)) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(p.shelter.name) LIKE LOWER(CONCAT('%', :searchInput, '%'))")
    List<Pet> searchPets(@Param("searchInput") String searchInput);
    //Employee
    @Query("SELECT p FROM Pet p " +
            "WHERE LOWER(p.shelter.name) = LOWER(:shelterName) AND " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(CAST(p.isMale AS string)) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(CAST(p.isHouseTrained AS string)) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(CAST(p.age AS string)) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(p.healthStatus) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(p.behaviour) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(p.breed) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(p.species) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(CAST(p.isSpayed AS string)) LIKE LOWER(CONCAT('%', :searchInput, '%')))")
    List<Pet> searchPets(@Param("searchInput") String searchInput, @Param("shelterName") String shelterName);

}