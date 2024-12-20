package com.example.pet_shelter_boot.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDTO(

        @Null
        Long id,

        @Size(max = 30)
        String name,

        @Email
        String email,

        @Positive
        Integer age,

        @Size(max = 0)
        List<PetDTO> pets
) {
}