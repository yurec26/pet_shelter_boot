package com.example.pet_shelter_boot.controller;

import com.example.pet_shelter_boot.dto.UserDTO;
import com.example.pet_shelter_boot.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        logger.info("All entities requested from user repository");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable("id") Long id
    ) {
        logger.info("Attempt to request user with id: %s".formatted(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @RequestBody @Valid UserDTO userToCreate
    ) {
        logger.info("Attempt to create user with name: %s"
                .formatted(userToCreate.getName()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userToCreate));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable("id") Long id
    ) {
        logger.info("Attempt to delete user with id: %s".formatted(id));
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(
            @PathVariable("id") Long id,
            @RequestBody @Valid UserDTO userToUpdate
    ) {
        logger.info("Attempt to update user with id: %s".formatted(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(userToUpdate, id));
    }
}
