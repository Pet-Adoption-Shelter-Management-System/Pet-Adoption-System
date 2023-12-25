package com.example.Backend.Services.PetServices;

import com.example.Backend.DTO.PetDto;
import com.example.Backend.Model.Pet;
import com.example.Backend.Model.PetVaccination;
import com.example.Backend.Model.Shelter;
import com.example.Backend.Repositories.DocumentRepository;
import com.example.Backend.Repositories.PetRepository;
import com.example.Backend.Repositories.PetVaccinationRepository;
import com.example.Backend.Repositories.ShelterRepository;
import com.example.Backend.Services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
//@Transactional
public class EditPetService extends AbstractPetService {
    private final PetRepository petRepository;
    private final ShelterRepository shelterRepository;
    private final PetVaccinationRepository petVaccinationRepository;
    private final DocumentRepository documentRepository;
    private final DocumentService documentService;

    @Override
    public void processPet(PetDto petDto, MultipartFile[] docs) throws IOException {
        Optional<Pet> petOptional = petRepository.findById(petDto.getId());
        if (petOptional.isEmpty()) {
            throw new RuntimeException("Pet does not exist");
        }
        Optional<Shelter> optionalShelter = shelterRepository.findByName(petDto.getShelterName());
        if (optionalShelter.isEmpty()) {
            throw new RuntimeException("Shelter does not exist");
        }
        try {
            Pet pet = petOptional.get();
            setPet(petDto, pet, optionalShelter.get());
            petVaccinationRepository.deleteByPet(pet);
            setVaccinations(petDto.getPetVaccinations(), pet);
            if (docs.length > 0) {
                documentRepository.deleteByPet(pet);
                setDocs(documentService.saveDocs(docs, petDto.getId()), pet);
            }
            List<PetVaccination> petVaccinations = pet.getPetVaccinations();
            for (PetVaccination petVaccination : petVaccinations) {
                System.out.println(petVaccination.getVaccination());
            }
            petRepository.save(pet);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSuccessMessage() {
        return "Pet updated successfully";
    }
}
