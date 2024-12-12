package com.example.pet_shelter_boot.service;

import com.example.pet_shelter_boot.dto.PetDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PetService {

    private final UserService userService;

    private Long idCounter;
    private final HashMap<Long, PetDTO> pets;

    public PetService(UserService userService) {
        this.userService = userService;
        idCounter = 0L;
        pets = new HashMap<>();
    }
    public List<PetDTO> getAll() {
        return pets.values().stream().toList();
    }

    public PetDTO getPetById(Long id) {
        return Optional.ofNullable(pets.get(id)).orElseThrow(()
                -> new NoSuchElementException("Такой питомец не найден = %s".formatted(id)));
    }

    public PetDTO createPet(PetDTO petToCreate) {
        Long newId = ++idCounter;
        PetDTO newPet = new PetDTO(
                newId,
                petToCreate.getName(),
                petToCreate.getUserId()
        );
        userService.givePetToUser(newPet);
        pets.put(newId, newPet);
        return newPet;
    }

    public void deletePet(Long id) {
        PetDTO petToRemove = pets.remove(getPetById(id).getId());
        userService.takePetFromUser(petToRemove);
    }

    public PetDTO updatePet(PetDTO petToUpdate, Long id) {
        PetDTO pet = getPetById(id);
        userService.takePetFromUser(pet);
        PetDTO updatedPet = new PetDTO(
                id,
                petToUpdate.getName(),
                petToUpdate.getUserId()
        );
        userService.givePetToUser(updatedPet);
        pets.put(id, updatedPet);
        return updatedPet;
    }
}
