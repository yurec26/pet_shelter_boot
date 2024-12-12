package com.example.pet_shelter_boot.service;

import com.example.pet_shelter_boot.dto.PetDTO;
import com.example.pet_shelter_boot.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private Long idCounter;
    private final HashMap<Long, UserDTO> users;

    public UserService() {
        idCounter = 0L;
        users = new HashMap<>();
    }

    public List<UserDTO> getAll() {
        return users.values().stream().toList();
    }

    public UserDTO getUserById(Long id) {
        return Optional.ofNullable(users.get(id)).orElseThrow(()
                -> new NoSuchElementException("Такой пользователь не найден = %s".formatted(id)));
    }

    public UserDTO createUser(UserDTO userToCreate) {
        Long newId = ++idCounter;
        UserDTO newUser = new UserDTO(
                newId,
                userToCreate.getName(),
                userToCreate.getEmail(),
                userToCreate.getAge(),
                new ArrayList<>()
        );
        users.put(newId, newUser);
        return newUser;
    }

    public void deleteUser(Long id) {
        users.remove(getUserById(id).getId());
    }

    public UserDTO updateUser(UserDTO userToUpdate, Long id) {
        getUserById(id);
        UserDTO updatedUser = new UserDTO(
                id,
                userToUpdate.getName(),
                userToUpdate.getEmail(),
                userToUpdate.getAge(),
                userToUpdate.getPets()
        );
        users.put(id, updatedUser);
        return updatedUser;
    }

    public void givePetToUser(PetDTO pet){
        UserDTO user = getUserById(pet.getUserId());
        user.getPets().add(pet);
    }

    public void takePetFromUser(PetDTO pet){
        UserDTO user = getUserById(pet.getUserId());
        user.getPets().remove(pet);
    }
}

