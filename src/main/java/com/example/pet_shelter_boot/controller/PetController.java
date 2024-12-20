package com.example.pet_shelter_boot.controller;

import com.example.pet_shelter_boot.converter.PetDtoConverter;
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
    private final PetDtoConverter petDtoConverter;

    public PetController(PetService petService, PetDtoConverter petDtoConverter) {
        this.petService = petService;
        this.petDtoConverter = petDtoConverter;
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getAll() {
        logger.info("GET request with endpoint '/pets' received");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(petService.getAll()
                        .stream()
                        .map(petDtoConverter::toDto)
                        .toList()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPetById(
            @PathVariable("id") Long id
    ) {
        logger.info("GET request with endpoint '/pets/%s' received".formatted(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(petDtoConverter.toDto(petService.getPetById(id)));
    }

    @PostMapping
    public ResponseEntity<PetDTO> createPet(
            @RequestBody @Valid PetDTO petToCreate
    ) {
        logger.info("POST request with endpoint '/pets' and body '%s' received"
                .formatted(petToCreate));
        return (ResponseEntity
                .status(HttpStatus.CREATED)
                .body(petDtoConverter.toDto(petService
                        .createPet(petDtoConverter.toDomain(petToCreate)))));
    }


    @DeleteMapping("/{id}")
    public void deletePet(
            @PathVariable("id") Long id
    ) {
        logger.info("DELETE request with endpoint 'pets/%s' received".formatted(id));
        petService.deletePet(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> updatePet(
            @PathVariable("id") Long id,
            @RequestBody @Valid PetDTO petToUpdate
    ) {
        logger.info("PUT request with endpoint '/pets/%s' and body '%s' received"
                .formatted(id, petToUpdate));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(petDtoConverter.toDto(
                        petService.updatePet(
                                petDtoConverter.toDomain(petToUpdate), id)));
    }
}
