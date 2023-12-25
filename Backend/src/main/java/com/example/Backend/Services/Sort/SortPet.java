package com.example.Backend.Services.Sort;

import com.example.Backend.Model.Pet;
import com.example.Backend.Repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SortPet<T extends Comparable<T>> implements ISort {
    @Autowired
    PetRepository petRepo;


    @Override//findPetsByVaccineAndShelterOrderedByNameAsc
    public List<Pet> sortForEmployee(String sortBy, boolean order, String shelterName) {
        try {
            switch (sortBy) {
                case "isHouseTrained":
                    if (order) return petRepo.findAllByOrderByIsHouseTrainedAndShelter_NameAsc(shelterName);
                    else return petRepo.findAllByOrderByIsHouseTrainedAndShelter_NameDesc(shelterName);
                case "isSpayed":
                    if (order) return petRepo.findAllByOrderByIsSpayedAndShelter_NameAsc(shelterName);
                    else return petRepo.findAllByOrderByIsSpayedAndShelter_NameDesc(shelterName);
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Pet> sortForCustomer(String sortBy, boolean order) {
        try {
            switch (sortBy) { //house-training,vaccinations, or spaying/neutering status.
                case "isHouseTrained":
                    if (order) return petRepo.findAllByOrderByIsHouseTrainedAsc();
                    else return petRepo.findAllByOrderByIsHouseTrainedDesc();
                case "isSpayed":
                    if (order) return petRepo.findAllByOrderByIsSpayedAsc();
                    else return petRepo.findAllByOrderByIsSpayedDesc();
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
