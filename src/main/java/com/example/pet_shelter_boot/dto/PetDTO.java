package com.example.pet_shelter_boot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;


public class PetDTO {

    @Null
    private Long id;

    @Size(max = 30)
    @NotBlank
    private String name;

    @NotNull
    @JsonProperty("user-id")
    private Long userId;

    public PetDTO(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public PetDTO() {
    }

    public @Null Long getId() {
        return id;
    }

    public void setId(@Null Long id) {
        this.id = id;
    }

    public @Size(max = 30) @NotBlank String getName() {
        return name;
    }

    public void setName(@Size(max = 30) @NotBlank String name) {
        this.name = name;
    }

    public @NotNull Long getUserId() {
        return userId;
    }

    public void setUserId(@NotNull Long userId) {
        this.userId = userId;
    }
}
