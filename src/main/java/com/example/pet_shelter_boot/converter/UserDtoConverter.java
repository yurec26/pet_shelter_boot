package com.example.pet_shelter_boot.converter;

import com.example.pet_shelter_boot.dto.UserDTO;
import com.example.pet_shelter_boot.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDtoConverter {

    private final PetDtoConverter petDtoConverter;

    public UserDtoConverter(PetDtoConverter petDtoConverter) {
        this.petDtoConverter = petDtoConverter;
    }

    public UserDTO toDto(User user) {
        return new UserDTO(
                user.id(),
                user.name(),
                user.email(),
                user.age(),
                user.pets()
                        .stream()
                        .map(petDtoConverter::toDto)
                        .toList()
        );
    }

    public User toDomain(UserDTO user) {
        return new User(
                user.id(),
                user.name(),
                user.email(),
                user.age(),
                user.pets() == null ?
                        List.of() :
                        user.pets()
                                .stream()
                                .map(petDtoConverter::toDomain)
                                .toList()
        );
    }
}
