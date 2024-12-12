package com.example.pet_shelter_boot.controller;

import com.example.pet_shelter_boot.dto.PetDTO;
import com.example.pet_shelter_boot.service.PetService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/pets")
@RestController
public class PetController {

    private final Logger logger = LoggerFactory.getLogger(PetController.class);
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getAll() {
        logger.info("All entities requested from pets repository");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(petService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPetById(
            @PathVariable("id") Long id
    ) {
        logger.info("Attempt to request pet with id: %s".formatted(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(petService.getPetById(id));
    }

    @PostMapping
    public ResponseEntity<PetDTO> createPet(
            @RequestBody @Valid PetDTO petToCreate
    ) {
        logger.info("Attempt to create pet with name: %s and user_id : %d"
                .formatted(petToCreate.getName(), petToCreate.getUserId()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(petService.createPet(petToCreate));
    }

    @DeleteMapping("/{id}")
    public void deletePet(
            @PathVariable("id") Long id
    ) {
        logger.info("Attempt to delete pet with id: %s".formatted(id));
        petService.deletePet(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> updatePet(
            @PathVariable("id") Long id,
            @RequestBody @Valid PetDTO petToUpdate
    ) {
        logger.info("Attempt to update pet with id: %s".formatted(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(petService.updatePet(petToUpdate, id));
    }
}
