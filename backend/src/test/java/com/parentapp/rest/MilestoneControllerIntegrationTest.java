package com.parentapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parentapp.fixture.MilestoneDTOFixture;
import com.parentapp.milestone.MilestoneController;
import com.parentapp.milestone.MilestoneDTO;
import com.parentapp.milestone.MilestoneService;
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
@WebMvcTest(MilestoneController.class)
public class MilestoneControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MilestoneService milestoneService;
    @Autowired
    private ObjectMapper objectMapper;

    private MilestoneDTO testMilestoneDTO;
    private List<MilestoneDTO> testMilestoneDTOs;

    @BeforeEach
    public void setup() {
        testMilestoneDTO = MilestoneDTOFixture.getTestMilestoneDTO();
        testMilestoneDTOs = MilestoneDTOFixture.testMilestoneDTOs();
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    public void getAllMilestones_whenInvoked_return200() throws Exception {
        when(milestoneService.findAll()).thenReturn(testMilestoneDTOs);

        mockMvc.perform(get("/api/milestones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(testMilestoneDTO.getId())))
                .andExpect(jsonPath("$[1].id", Matchers.is(testMilestoneDTOs.get(1).getId())));
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    public void getMilestoneById_whenInvoked_return200AndMilestoneDTO() throws Exception {
        when(milestoneService.get(testMilestoneDTO.getId())).thenReturn(testMilestoneDTO);

        mockMvc.perform(get("/api/milestones/{id}", testMilestoneDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(testMilestoneDTO.getId())))
                .andExpect(jsonPath("$.name", Matchers.is(testMilestoneDTO.getName())))
                .andExpect(jsonPath("$.description", Matchers.is(testMilestoneDTO.getDescription())));
    }
    @Test
    public void createMilestone_whenInvoked_returnsMilestone() throws Exception {
        when(milestoneService.create(testMilestoneDTO)).thenReturn(testMilestoneDTO.getId());

        mockMvc.perform(post("/api/milestones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMilestoneDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/api/milestones/")));
    }
    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void updateMilestone_milestoneIdAndMilestoneDTO_ReturnsOkStatus() throws Exception {
        mockMvc.perform(put("/api/milestones/{id}", testMilestoneDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMilestoneDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void deleteMilestone_milestoneId_ReturnsNoContentStatus() throws Exception {
        mockMvc.perform(delete("/api/milestones/{id}", testMilestoneDTO.getId()))
                .andExpect(status().isNoContent());
    }
}
