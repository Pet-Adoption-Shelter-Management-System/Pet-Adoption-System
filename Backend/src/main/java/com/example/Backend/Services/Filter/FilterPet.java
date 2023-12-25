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
    public List<Pet> meetCriteriaEmployee(String criteria, String toMeet, long shelterId) {
        try {
            //species, breed, age, and shelter location.
            return switch (criteria) {
                case "species" -> petRepository.findBySpeciesNameAndShelter(toMeet, shelterId);
                case "breed" -> petRepository.findByBreedAndShelter(toMeet, shelterId);
                case "age" -> petRepository.findByAgeAndShelter(Float.parseFloat(toMeet), shelterId);
                case "shelter location" -> petRepository.findByShelterLocationAndShelter(toMeet, shelterId);
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
                case "species" -> petRepository.findBySpeciesName(toMeet);
                case "breed" -> petRepository.findByBreed(toMeet);
                case "age" -> petRepository.findByAge(Float.parseFloat(toMeet));
                case "shelter location" -> petRepository.findByShelterLocation(toMeet);
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }
}
