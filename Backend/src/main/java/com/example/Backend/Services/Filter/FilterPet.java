package com.example.Backend.Services.Filter;

import com.example.Backend.Model.Pet;
import com.example.Backend.Repositories.PetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FilterPet<T extends Comparable<T>> implements IFilter {
    @Autowired
    PetRepository petRepository;

    @Override
    public List<Pet> meetCriteriaEmployee(String criteria, String toMeet, String shelterName) {
        try {
            //species, breed, age, and shelter location.
            return switch (criteria) {
                case "species" -> petRepository.findBySpeciesAndShelter_Name(toMeet, shelterName);
                case "breed" -> petRepository.findByBreedAndShelter_Name(toMeet, shelterName);
                case "age" -> petRepository.findByAgeAndShelter_Name(Float.parseFloat(toMeet), shelterName);
                case "shelter location" -> petRepository.findByShelterLocationAndShelter_Name(toMeet, shelterName);
                case "vaccination" -> petRepository.findByPetVaccinations_VaccinationAndShelter_Name(toMeet,shelterName);
                case "search" -> petRepository.searchPets(toMeet,shelterName);
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Pet> meetCriteriaCustomer(String criteria, String toMeet) {
        try {
            //species, breed, age, and shelter location.
            return switch (criteria) {
                case "species" -> petRepository.findBySpecies(toMeet);
                case "breed" -> petRepository.findByBreed(toMeet);
                case "age" -> petRepository.findByAge(Float.parseFloat(toMeet));
                case "shelter location" -> petRepository.findByShelterLocation(toMeet);
                case "vaccination" -> petRepository.findByPetVaccinations_Vaccination(toMeet);
                case "search" -> petRepository.searchPets(toMeet);
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }
}
