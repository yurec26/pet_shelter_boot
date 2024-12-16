package com.example.pet_shelter_boot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PetDTO(
        @Null Long id,

        @Size(max = 30)
        @NotBlank String name,

        @NotNull
        @JsonProperty("user_id")
        Long userId
) {
}
