package com.example.Backend.Services.Filter;

import com.example.Backend.Model.Pet;
import com.example.Backend.Repositories.PetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FilterPet<T extends Comparable<T>> {
    @Autowired
    PetRepository petRepository;


    public List<Pet> meetCriteriaEmployee(String criteria, String toMeet, String shelterName) {
        try {
            return switch (criteria) {
//                case "id" -> petRepository.findByIdAndShelter_Name(Long.parseLong(toMeet),shelterName);
                case "name" -> petRepository.findByNameAndShelter_Name(toMeet, shelterName);
                case "behaviour" -> petRepository.findByBehaviourAndShelter_Name(toMeet, shelterName);
                case "description" -> petRepository.findByDescriptionAndShelter_Name(toMeet, shelterName);
                case "healthStatus" -> petRepository.findByHealthStatusAndShelter_Name(toMeet, shelterName);
                case "isHouseTrained" ->
                        petRepository.findByIsHouseTrainedAndShelter_Name(Boolean.parseBoolean(toMeet), shelterName);
                case "isMale" -> petRepository.findByIsMaleAndShelter_Name(Boolean.parseBoolean(toMeet), shelterName);
                case "isSpayed" ->
                        petRepository.findByIsSpayedAndShelter_Name(Boolean.parseBoolean(toMeet), shelterName);
                case "shelterId" -> petRepository.findByShelter_IdAndShelter_Name(Long.parseLong(toMeet), shelterName);
                case "species" -> petRepository.findBySpeciesAndShelter_Name(toMeet, shelterName);
                case "breed" -> petRepository.findByBreedAndShelter_Name(toMeet, shelterName);
                case "age" -> petRepository.findByAgeAndShelter_Name(Float.parseFloat(toMeet), shelterName);
                case "shelterLocation" -> petRepository.findByShelterLocationAndShelter_Name(toMeet, shelterName);
                case "vaccination" ->
                        petRepository.findByPetVaccinations_VaccinationAndShelter_Name(toMeet, shelterName);
                case "search" -> petRepository.searchPets(toMeet, shelterName);
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }


    public List<Pet> meetCriteriaCustomer(String criteria, String toMeet) {
        try {
            return switch (criteria) {
//                case "id" -> petRepository.findById(Long.parseLong(toMeet));
                case "name" -> petRepository.findByName(toMeet);
                case "behaviour" -> petRepository.findByBehaviour(toMeet);
                case "description" -> petRepository.findByDescription(toMeet);
                case "healthStatus" -> petRepository.findByHealthStatus(toMeet);
                case "isHouseTrained" -> petRepository.findByIsHouseTrained(Boolean.parseBoolean(toMeet));
                case "isMale" -> petRepository.findByIsMale(Boolean.parseBoolean(toMeet));
                case "isSpayed" -> petRepository.findByIsSpayed(Boolean.parseBoolean(toMeet));
                case "shelterId" -> petRepository.findByShelter_Id(Long.parseLong(toMeet));
                case "species" -> petRepository.findBySpecies(toMeet);
                case "breed" -> petRepository.findByBreed(toMeet);
                case "age" -> petRepository.findByAge(Float.parseFloat(toMeet));
                case "shelterLocation" -> petRepository.findByShelterLocation(toMeet);
                case "vaccination" -> petRepository.findByPetVaccinations_Vaccination(toMeet);
                case "shelterName" -> petRepository.findByShelter_Name(toMeet);
                case "search" -> petRepository.searchPets(toMeet);
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }
}
