package com.example.Backend.Services.Sort;

import com.example.Backend.Model.Pet;
import com.example.Backend.Repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SortPet<T extends Comparable<T>> implements ISort {
    @Autowired
    PetRepository petRepo;


    @Override//findPetsByVaccineAndShelterOrderedByNameAsc
    public List<Pet> sortForEmployee(String sortBy, boolean order, String shelterName) {
        try {
            switch (sortBy) {
                case "isHouseTrained":
                    if (order) return petRepo.findAllByShelter_NameOrderByIsHouseTrainedAsc(shelterName);
                    else return petRepo.findAllByShelter_NameOrderByIsHouseTrainedDesc(shelterName);
                case "isSpayed":
                    if (order) return petRepo.findAllByShelter_NameOrderByIsSpayedAsc(shelterName);
                    else return petRepo.findAllByShelter_NameOrderByIsSpayedDesc(shelterName);
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
