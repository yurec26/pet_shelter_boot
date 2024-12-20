package com.example.pet_shelter_boot.service;

import com.example.pet_shelter_boot.converter.PetEntityConverter;
import com.example.pet_shelter_boot.model.Pet;
import com.example.pet_shelter_boot.repository.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PetService {

    private final UserService userService;
    private final PetEntityConverter petEntityConverter;
    private final PetRepository petRepository;

    public PetService(UserService userService,
                      PetEntityConverter petEntityConverter,
                      PetRepository petRepository) {
        this.userService = userService;
        this.petEntityConverter = petEntityConverter;
        this.petRepository = petRepository;
    }

    public List<Pet> getAll() {
        return petRepository.findAll()
                .stream()
                .map(petEntityConverter::toDomain)
                .toList();
    }

    public Pet getPetById(Long id) {
        return petEntityConverter.toDomain(petRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Такой питомец не найден = %s".formatted(id))));
    }

    public Pet createPet(Pet petToCreate) {
        checkUserExistence(petToCreate.userId());
        return petEntityConverter.toDomain(
                petRepository.save(petEntityConverter.toEntity(petToCreate))
        );
    }

    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new EntityNotFoundException("Такой питомец не найден = %s".formatted(id));
        }
        petRepository.deleteById(id);
    }


    public Pet updatePet(Pet petToUpdate, Long id) {
        if (!petRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Сущность не найдена id : %s".formatted(id));
        }
        checkUserExistence(petToUpdate.userId());
        petRepository.updatePet(
                id,
                petToUpdate.name(),
                petToUpdate.userId()
        );

        return petEntityConverter.toDomain(
                petRepository.findById(id).orElseThrow()
        );

    }

    void checkUserExistence(Long id) {
        userService.throwOnUserDoesNotExist(id);
    }
}
