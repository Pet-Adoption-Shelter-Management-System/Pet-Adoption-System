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
//    List<Pet> findByAge(float age);
    List<Pet> findByAgeBetween(float lowerBound, float upperBound);


    //    List<Pet> findByAgeAndShelter_Name(float age, String shelterName);
List<Pet> findByAgeBetweenAndShelter_Name(float lowerBound, float upperBound, String shelterName);


    //-------------------------------------------------------------------------------------------
    //shelter location
    List<Pet> findByShelterLocation(String location);

    List<Pet> findByShelterLocationAndShelter_Name(String location, String shelterName);

    //-----------------------------------------------------------------------------------------
    //vaccination
    List<Pet> findByPetVaccinations_Vaccination(String vaccine);

    List<Pet> findByPetVaccinations_VaccinationAndShelter_Name(String vaccine, String shelterName);

    //-------------------------------------------------------------------------------------------
//    //id
//    List<Pet> findById(long toMeet);
//
//
//    List<Pet> findByIdAndShelter_Name(long toMeet, String shelterName);

    //Name
    List<Pet> findByName(String name);

    List<Pet> findByNameAndShelter_Name(String name, String shelterName);

    //behaviour
    List<Pet> findByBehaviour(String behavior);

    List<Pet> findByBehaviourAndShelter_Name(String behaviour, String shelterName);

    //description
    List<Pet> findByDescription(String description);

    List<Pet> findByDescriptionAndShelter_Name(String description, String shelterName);

    //health status
    List<Pet> findByHealthStatus(String healthStatus);

    List<Pet> findByHealthStatusAndShelter_Name(String healthStatus, String shelterName);

    //is Trained
    List<Pet> findByIsHouseTrained(boolean isHouseTrained);

    List<Pet> findByIsHouseTrainedAndShelter_Name(boolean isHouseTrained, String shelterName);

    //gender
    List<Pet> findByIsMale(boolean isMale);

    List<Pet> findByIsMaleAndShelter_Name(boolean isMale, String shelterName);

    //status
    List<Pet> findByIsSpayed(boolean isSpayed);

    List<Pet> findByIsSpayedAndShelter_Name(boolean isSpayed, String shelterName);

    //shelter id
    List<Pet> findByShelter_Id(long shelterId);

    List<Pet> findByShelter_IdAndShelter_Name(long shelterId, String shelterName);

    //shelter name
    List<Pet> findByShelter_Name(String toMeet);
    //-------------------------------------------------------------------------------------------

    //Sort
    //-------------------------------------------------------------------------------------------
    //Employee
    List<Pet> findAllByShelter_NameOrderByIdAsc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByIdDesc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByAgeAsc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByAgeDesc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByBehaviourAsc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByBehaviourDesc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByBreedAsc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByBreedDesc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByDescriptionAsc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByDescriptionDesc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByHealthStatusAsc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByHealthStatusDesc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByIsHouseTrainedAsc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByIsHouseTrainedDesc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByIsMaleAsc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByIsMaleDesc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByIsSpayedAsc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByIsSpayedDesc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByNameAsc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByNameDesc(String shelterName);

    List<Pet> findAllByShelter_NameOrderBySpeciesAsc(String shelterName);

    List<Pet> findAllByShelter_NameOrderBySpeciesDesc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByShelterIdAsc(String shelterName);

    List<Pet> findAllByShelter_NameOrderByShelterIdDesc(String shelterName);
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