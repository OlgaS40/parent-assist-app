package com.parentapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parentapp.fixture.UserDTOFixture;
import com.parentapp.users.UserController;
import com.parentapp.users.UserDTO;
import com.parentapp.auth.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO testUserDTO;
    private List<UserDTO> testUserDTOs;

    @BeforeEach
    public void setup() {
        testUserDTO = UserDTOFixture.testUserDTO();
        testUserDTOs = UserDTOFixture.userDTOList();
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    public void getAllUsers_whenInvoked_return200() throws Exception {
        when(userService.findAllAsDTO()).thenReturn(testUserDTOs);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(testUserDTO.getId())))
                .andExpect(jsonPath("$[1].id", Matchers.is(testUserDTOs.get(1).getId())));
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    public void getUser_whenInvoked_return200AndUserDTO() throws Exception {
        when(userService.get(testUserDTO.getId())).thenReturn(testUserDTO);

        mockMvc.perform(get("/api/users/{id}", testUserDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(testUserDTO.getId())))
                .andExpect(jsonPath("$.username", Matchers.is(testUserDTO.getUsername())))
                .andExpect(jsonPath("$.email", Matchers.is(testUserDTO.getEmail())));
    }
    @Test
    public void createUser_whenInvoked_returnsUser() throws Exception {
        when(userService.create(testUserDTO)).thenReturn(testUserDTO.getId());

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/api/users/")));
    }
    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void updateUser_userIdAndUserDTO_ReturnsOkStatus() throws Exception {
        mockMvc.perform(put("/api/users/{id}", testUserDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void deleteUser_userId_ReturnsNoContentStatus() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", testUserDTO.getId()))
                .andExpect(status().isNoContent());
    }
}
