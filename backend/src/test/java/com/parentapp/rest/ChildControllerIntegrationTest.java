package com.parentapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parentapp.child.ChildController;
import com.parentapp.child.ChildDTO;
import com.parentapp.child.ChildService;
import com.parentapp.fixture.ChildDTOFixture;
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
@WebMvcTest(ChildController.class)
public class ChildControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ChildService childService;
    @Autowired
    private ObjectMapper objectMapper;

    private ChildDTO testChildDTO;
    private List<ChildDTO> testChildDTOs;

    @BeforeEach
    public void setup() {
        testChildDTO = ChildDTOFixture.testChild();
        testChildDTOs = ChildDTOFixture.testChildDTOs();
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    public void getAllChildren_whenInvoked_return200() throws Exception {
        when(childService.findAll()).thenReturn(testChildDTOs);

        mockMvc.perform(get("/api/children"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(testChildDTO.getId())))
                .andExpect(jsonPath("$[1].id", Matchers.is(testChildDTOs.get(1).getId())));
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    public void getChildById_whenInvoked_return200AndChildDTO() throws Exception {
        when(childService.get(testChildDTO.getId())).thenReturn(testChildDTO);

        mockMvc.perform(get("/api/children/{id}", testChildDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(testChildDTO.getId())))
                .andExpect(jsonPath("$.name", Matchers.is(testChildDTO.getName())))
                .andExpect(jsonPath("$.gender", Matchers.is(testChildDTO.getGender().toString())));
    }
    @Test
    public void createChild_whenInvoked_returnsChild() throws Exception {
        when(childService.create(testChildDTO)).thenReturn(testChildDTO.getId());

        mockMvc.perform(post("/api/children")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testChildDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/api/children/")));
    }
    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void updateChild_childIdAndChildDTO_ReturnsOkStatus() throws Exception {
        mockMvc.perform(put("/api/children/{id}", testChildDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testChildDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void deleteChild_childId_ReturnsNoContentStatus() throws Exception {
        mockMvc.perform(delete("/api/children/{id}", testChildDTO.getId()))
                .andExpect(status().isNoContent());
    }
}
