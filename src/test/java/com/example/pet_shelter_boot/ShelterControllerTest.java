package com.example.pet_shelter_boot;

import com.example.pet_shelter_boot.dto.PetDTO;
import com.example.pet_shelter_boot.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createUserSuccess() throws Exception {
        UserDTO userDTO = new UserDTO(
                null,
                "Pidar",
                "rrr@inbox.ru",
                12,
                null
        );

        String jsonUser = objectMapper.writeValueAsString(userDTO);
        String responseJson = mockMvc.perform(MockMvcRequestBuilders.post("/users").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser))
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDTO responseUser = objectMapper.readValue(responseJson, UserDTO.class);
        Assertions.assertEquals(userDTO.getName(), responseUser.getName());
        Assertions.assertNotNull(responseUser.getId());
    }

    @Test
    void getByIdUserSuccess() throws Exception {
        long requestedId = 1L;
        String uri = "/users/" + requestedId;
        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get(uri).
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDTO responseUser = objectMapper.readValue(responseJson, UserDTO.class);
        Assertions.assertEquals(requestedId, responseUser.getId());
        Assertions.assertNotNull(responseUser.getId());
    }

    @Test
    void createPetSuccess() throws Exception {
        PetDTO petDTO = new PetDTO(
                null,
                "Sobaka pidara",
                1L
        );

        String jsonPet = objectMapper.writeValueAsString(petDTO);
        String responseJson = mockMvc.perform(MockMvcRequestBuilders.post("/pets").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPet))
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDTO responsePet = objectMapper.readValue(responseJson, PetDTO.class);
        Assertions.assertEquals(petDTO.getName(), responsePet.getName());
        Assertions.assertNotNull(responsePet.getId());
    }

    @Test
    void getByIdPetSuccess() throws Exception {
        long requestedId = 1L;
        String uri = "/pets/" + requestedId;
        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get(uri).
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDTO responseUser = objectMapper.readValue(responseJson, PetDTO.class);
        Assertions.assertEquals(requestedId, responseUser.getId());
        Assertions.assertNotNull(responseUser.getId());
    }
}