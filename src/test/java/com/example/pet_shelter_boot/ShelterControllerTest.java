package com.example.pet_shelter_boot;

import com.example.pet_shelter_boot.dto.PetDTO;
import com.example.pet_shelter_boot.dto.UserDTO;
import com.example.pet_shelter_boot.model.User;
import com.example.pet_shelter_boot.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createPetSuccess() throws Exception {
        User testUser = createTestUser();
        PetDTO petDTO = new PetDTO(
                null,
                "Sobaka pidara",
                testUser.id()
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
        Assertions.assertEquals(petDTO.name(), responsePet.name());
        Assertions.assertNotNull(responsePet.id());
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
        Assertions.assertEquals(requestedId, responseUser.id());
        Assertions.assertNotNull(responseUser.id());
    }

    public User createTestUser(){
        return userService.createUser(new User(
                null,
                "user-name",
                "spbpu@mail.ru",
                19,
                List.of()
                ));
    }
}