package com.example.pet_shelter_boot.service;

import com.example.pet_shelter_boot.converter.UserEntityConverter;
import com.example.pet_shelter_boot.model.User;
import com.example.pet_shelter_boot.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserEntityConverter userEntityConverter;

    public UserService(UserRepository userRepository,
                       UserEntityConverter userEntityConverter) {
        this.userRepository = userRepository;
        this.userEntityConverter = userEntityConverter;
    }

    public List<User> getAll() {
        return userRepository.findAllWithPets()
                .stream()
                .map(userEntityConverter::toDomain)
                .toList();
    }

    public User getUserById(Long id) {
        return userEntityConverter.toDomain(
                userRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Сущность не найдена id : %s".formatted(id))));
    }

    public User createUser(User userToCreate) {
        return userEntityConverter.toDomain(
                userRepository
                        .save(userEntityConverter.toEntity(userToCreate))
        );
    }

    public void deleteUser(Long id) {
        throwOnUserDoesNotExist(id);
        throwOnUserHasPets(id);
        userRepository.deleteById(id);
    }

    public User updateUser(User userToUpdate, Long id) {
        throwOnUserDoesNotExist(id);
        userRepository.updateUser(
                id,
                userToUpdate.name(),
                userToUpdate.email(),
                userToUpdate.age()
        );
        return userEntityConverter.toDomain(
                userRepository.findById(id).orElseThrow()
        );
    }

    void throwOnUserDoesNotExist(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Сущность не найдена id : %s".formatted(id));
        }
    }

    void throwOnUserHasPets(Long id) {
        User user = userEntityConverter
                .toDomain(userRepository.findById(id).orElseThrow());
        if (!user.pets().isEmpty()) {
            throw new IllegalArgumentException
                    ("У пользователя есть питомцы, сначала раздайте их другим");
        }
    }
}

