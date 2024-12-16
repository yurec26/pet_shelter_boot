package com.example.pet_shelter_boot.converter;

import com.example.pet_shelter_boot.dto.PetDTO;
import com.example.pet_shelter_boot.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetDtoConverter {

    public PetDTO toDto(Pet pet) {
        return new PetDTO(
                pet.id(),
                pet.name(),
                pet.userId()
        );
    }

    public Pet toDomain(PetDTO pet) {
        return new Pet(
                pet.id(),
                pet.name(),
                pet.userId()
        );
    }
}
