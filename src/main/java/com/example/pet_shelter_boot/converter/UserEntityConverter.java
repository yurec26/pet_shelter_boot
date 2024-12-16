package com.example.pet_shelter_boot.converter;

import com.example.pet_shelter_boot.entity.UserEntity;
import com.example.pet_shelter_boot.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserEntityConverter {

    private final PetEntityConverter petEntityConverter;

    public UserEntityConverter(PetEntityConverter petEntityConverter) {
        this.petEntityConverter = petEntityConverter;
    }


    public UserEntity toEntity(User user) {
        return new UserEntity(
                user.id(),
                user.name(),
                user.email(),
                user.age(),
                user.pets()
                        .stream()
                        .map(petEntityConverter::toEntity)
                        .collect(Collectors.toSet()));
    }

    public User toDomain(UserEntity user) {
        return new User(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getPets()
                        .stream()
                        .map(petEntityConverter::toDomain)
                        .toList()
        );
    }
}
