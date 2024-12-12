package com.example.pet_shelter_boot.dto;


import jakarta.validation.constraints.*;

import java.util.List;

public class UserDTO {
    @Null
    private Long id;

    @Size(max = 30)
    @NotBlank
    private String name;

    @Email
    @NotNull
    private String email;

    @Positive
    @NotNull
    private Integer age;

    private List<PetDTO> pets;

    public UserDTO(Long id, String name, String email, Integer age, List<PetDTO> pets) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.pets = pets;
    }

    public UserDTO() {
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

    public @Email @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotNull String email) {
        this.email = email;
    }

    public @Positive @NotNull Integer getAge() {
        return age;
    }

    public void setAge(@Positive @NotNull Integer age) {
        this.age = age;
    }

    public List<PetDTO> getPets() {
        return pets;
    }

    public void setPets(List<PetDTO> pets) {
        this.pets = pets;
    }
}