package com.example.pet_shelter_boot.converter;

import com.example.pet_shelter_boot.entity.PetEntity;
import com.example.pet_shelter_boot.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetEntityConverter {

    public PetEntity toEntity(Pet pet) {
        return new PetEntity(
                pet.id(),
                pet.name(),
                pet.userId()
        );
    }

    public Pet toDomain(PetEntity pet) {
        return new Pet(
                pet.getId(),
                pet.getName(),
                pet.getUserId()
        );
    }
}
