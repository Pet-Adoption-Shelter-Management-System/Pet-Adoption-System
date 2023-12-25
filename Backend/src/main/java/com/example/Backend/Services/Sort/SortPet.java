package com.example.Backend.Services.Sort;

import com.example.Backend.Model.Pet;
import com.example.Backend.Repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

public class SortPet<T extends Comparable<T>> implements ISort {
    @Autowired
    PetRepository petRepo;


    @Override//findPetsByVaccineAndShelterOrderedByNameAsc
    public List<Pet> sortForEmployee(String sortBy, boolean order, long shelterId) {
        try {
            switch (sortBy) {
                case "isHouseTrained":
                    if (order) return petRepo.findPetsByHouseTrainAndShelterOrderedByNameAsc(shelterId);
                    else return petRepo.findPetsByHouseTrainAndShelterOrderedByNameDesc(shelterId);
                case "isSpayed":
                    if (order) return petRepo.findPetsByStatusAndShelterOrderedByNameAsc(shelterId);
                    else return petRepo.findPetsByStatusAndShelterOrderedByNameDesc(shelterId);
                case "vaccination":
                    if (order) return petRepo.findPetsByVaccineAndShelterOrderedByNameAsc(shelterId);
                    else return petRepo.findPetsByVaccineAndShelterOrderedByNameDesc(shelterId);
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
                    if (order) return petRepo.findAllByCustomer(Sort.by(Sort.Direction.ASC, "isHouseTrained"));
                    else return petRepo.findAllByCustomer(Sort.by(Sort.Direction.DESC, "isHouseTrained"));
                case "isSpayed":
                    if (order) return petRepo.findAllByCustomer(Sort.by(Sort.Direction.ASC, "isSpayed"));
                    else return petRepo.findAllByCustomer(Sort.by(Sort.Direction.DESC, "isSpayed"));
                case "vaccination":
                    if (order) return petRepo.findByVaccinationOrderByNameAsc();
                    else return petRepo.findByVaccinationOrderByNameDesc();
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
