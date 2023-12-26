package com.example.Backend.Services.Sort;

import com.example.Backend.Model.Pet;
import com.example.Backend.Repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SortPet<T extends Comparable<T>> implements ISort {
    @Autowired
    PetRepository petRepo;


    @Override
    public List<Pet> sortForEmployee(String sortBy, boolean order, String shelterName) {
        try {
            switch (sortBy) {
                case "id":
                    if (order) return petRepo.findAllByShelter_NameOrderByIdAsc(shelterName);
                    else return petRepo.findAllByShelter_NameOrderByIdDesc(shelterName);
                case "age":
                    if (order) return petRepo.findAllByShelter_NameOrderByAgeAsc(shelterName);
                    else return petRepo.findAllByShelter_NameOrderByAgeDesc(shelterName);
                case "behaviour":
                    if (order) return petRepo.findAllByShelter_NameOrderByBehaviourAsc(shelterName);
                    else return petRepo.findAllByShelter_NameOrderByBehaviourDesc(shelterName);
                case "breed":
                    if (order) return petRepo.findAllByShelter_NameOrderByBreedAsc(shelterName);
                    else return petRepo.findAllByShelter_NameOrderByBreedDesc(shelterName);
                case "description":
                    if (order) return petRepo.findAllByShelter_NameOrderByDescriptionAsc(shelterName);
                    else return petRepo.findAllByShelter_NameOrderByDescriptionDesc(shelterName);
                case "healthStatus":
                    if (order) return petRepo.findAllByShelter_NameOrderByHealthStatusAsc(shelterName);
                    else return petRepo.findAllByShelter_NameOrderByHealthStatusDesc(shelterName);
                case "isHouseTrained":
                    if (order) return petRepo.findAllByShelter_NameOrderByIsHouseTrainedAsc(shelterName);
                    else return petRepo.findAllByShelter_NameOrderByIsHouseTrainedDesc(shelterName);
                case "isMale":
                    if (order) return petRepo.findAllByShelter_NameOrderByIsMaleAsc(shelterName);
                    else return petRepo.findAllByShelter_NameOrderByIsMaleDesc(shelterName);
                case "isSpayed":
                    if (order) return petRepo.findAllByShelter_NameOrderByIsSpayedAsc(shelterName);
                    else return petRepo.findAllByShelter_NameOrderByIsSpayedDesc(shelterName);
                case "name":
                    if (order) return petRepo.findAllByShelter_NameOrderByNameAsc(shelterName);
                    else return petRepo.findAllByShelter_NameOrderByNameDesc(shelterName);
                case "species":
                    if (order) return petRepo.findAllByShelter_NameOrderBySpeciesAsc(shelterName);
                    else return petRepo.findAllByShelter_NameOrderBySpeciesDesc(shelterName);
                case "shelterId":
                    if (order) return petRepo.findAllByShelter_NameOrderByShelterIdAsc(shelterName);
                    else return petRepo.findAllByShelter_NameOrderByShelterIdDesc(shelterName);
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
            switch (sortBy) {
                case "id":
                    if (order) return petRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
                    else return petRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
                case "age":
                    if (order) return petRepo.findAll(Sort.by(Sort.Direction.ASC, "age"));
                    else return petRepo.findAll(Sort.by(Sort.Direction.DESC, "age"));
                case "behaviour":
                    if (order) return petRepo.findAll(Sort.by(Sort.Direction.ASC, "behaviour"));
                    else return petRepo.findAll(Sort.by(Sort.Direction.DESC, "behaviour"));
                case "breed":
                    if (order) return petRepo.findAll(Sort.by(Sort.Direction.ASC, "breed"));
                    else return petRepo.findAll(Sort.by(Sort.Direction.DESC, "breed"));
                case "description":
                    if (order) return petRepo.findAll(Sort.by(Sort.Direction.ASC, "description"));
                    else return petRepo.findAll(Sort.by(Sort.Direction.DESC, "description"));
                case "healthStatus":
                    if (order) return petRepo.findAll(Sort.by(Sort.Direction.ASC, "healthStatus"));
                    else return petRepo.findAll(Sort.by(Sort.Direction.DESC, "healthStatus"));
                case "isHouseTrained":
                    if (order) return petRepo.findAll(Sort.by(Sort.Direction.ASC, "isHouseTrained"));
                    else return petRepo.findAll(Sort.by(Sort.Direction.DESC, "isHouseTrained"));
                case "isMale":
                    if (order) return petRepo.findAll(Sort.by(Sort.Direction.ASC, "isMale"));
                    else return petRepo.findAll(Sort.by(Sort.Direction.DESC, "isMale"));
                case "isSpayed":
                    if (order) return petRepo.findAll(Sort.by(Sort.Direction.ASC, "isSpayed"));
                    else return petRepo.findAll(Sort.by(Sort.Direction.DESC, "isSpayed"));
                case "name":
                    if (order) return petRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
                    else return petRepo.findAll(Sort.by(Sort.Direction.DESC, "name"));
                case "species":
                    if (order) return petRepo.findAll(Sort.by(Sort.Direction.ASC, "species"));
                    else return petRepo.findAll(Sort.by(Sort.Direction.DESC, "species"));
                case "shelterId":
                    if (order) return petRepo.findAll(Sort.by(Sort.Direction.ASC, "shelterId"));
                    else return petRepo.findAll(Sort.by(Sort.Direction.DESC, "shelterId"));
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
