package com.parentapp.services;

import com.parentapp.activity.ActivityRepository;
import com.parentapp.fixture.MilestoneDTOFixture;
import com.parentapp.fixture.MilestoneFixture;
import com.parentapp.milestone.Milestone;
import com.parentapp.milestone.MilestoneDTO;
import com.parentapp.milestone.MilestoneRepository;
import com.parentapp.milestone.MilestoneService;
import com.parentapp.util.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MilestoneServiceTest {
    @Mock
    private MilestoneRepository milestoneRepository;
    @Mock
    private ActivityRepository activityRepository;
    @InjectMocks
    private MilestoneService milestoneService;
    private Milestone testMilestone;
    private MilestoneDTO testMilestoneDTO;
    private List<Milestone> testMilestones;
    private List<MilestoneDTO> testMilestoneDTOs;

    @BeforeEach
    public void setup() {
        testMilestone = MilestoneFixture.getTestMilestone();
        testMilestoneDTO = MilestoneDTOFixture.getTestMilestoneDTO();
        testMilestones = MilestoneFixture.testMilestones();
        testMilestoneDTOs = MilestoneDTOFixture.testMilestoneDTOs();
    }
    @Test
    void findAll_whenInvoked_returnsAListWithTwoMilestones() {
        when(milestoneRepository.findAll()).thenReturn(testMilestones);
        assertThat(milestoneService.findAll())
                .isNotEmpty()
                .containsExactlyInAnyOrderElementsOf(testMilestoneDTOs);
    }
    @Test
    void get_MilestoneId_returnsMilestoneDTO() {
        when(milestoneRepository.findById(anyString())).thenReturn(Optional.ofNullable(testMilestone));

        MilestoneDTO milestone = milestoneService.get("testMilestone01");
        assertThat(milestone).isNotNull();
        assertThat(milestone).isEqualTo(testMilestoneDTO);
    }
    @Test
    void get_NonExistentId_throwNotFoundException() {
        when(milestoneRepository.findById(anyString())).thenReturn(Optional.empty());
        String id = testMilestone.getId();

        assertThrows(NotFoundException.class, () -> milestoneService.get(id));
        verify(milestoneRepository, times(1)).findById(id);
    }
    @Test
    void create_whenInvoked_returnsMilestoneId() {
        when(milestoneRepository.save(any(Milestone.class))).thenReturn(testMilestone);
        String createdMilestoneId = milestoneService.create(testMilestoneDTO);

        verify(milestoneRepository, times(1)).save(any(Milestone.class));
        assertThat(createdMilestoneId).isNotNull();
        assertThat(createdMilestoneId).isEqualTo(testMilestone.getId());
    }
    @Test
    void create_nonExistentActivity_returnsMilestoneId() {
        when(activityRepository.findById(anyString())).thenReturn(Optional.empty());
        MilestoneDTO milestoneDTOToCreate = testMilestoneDTO;
        milestoneDTOToCreate.setActivities(Set.of("nonExistentActivityId"));

        assertThrows(NotFoundException.class, () -> milestoneService.create(milestoneDTOToCreate));
        verify(milestoneRepository, times(0)).save(any(Milestone.class));
    }
    @Test
    void update_milestoneIdAndDTO_updatedMilestone() {
        MilestoneDTO updatedMilestoneDTO = testMilestoneDTO;
        String id = updatedMilestoneDTO.getId();
        updatedMilestoneDTO.setAgeFrom(2);
        when(milestoneRepository.findById(anyString())).thenReturn(Optional.of(testMilestone));

        Milestone updatedMilestone = testMilestone;
        updatedMilestone.setAgeFrom(2);


        milestoneService.update(id, updatedMilestoneDTO);

        verify(milestoneRepository, times(1)).findById(id);
        verify(milestoneRepository, times(1)).save(updatedMilestone);
    }
    @Test
    void update_NonExistentMilestone_throwsNotFoundException() {
        when(milestoneRepository.findById(anyString())).thenReturn(Optional.empty());

        MilestoneDTO updatedMilestoneDTO = testMilestoneDTO;
        String id = testMilestoneDTO.getId();
        Milestone updatedMilestone = testMilestone;

        assertThrows(NotFoundException.class, () -> milestoneService.update(id, updatedMilestoneDTO));

        verify(milestoneRepository, times(1)).findById(id);
        verify(milestoneRepository, times(0)).save(updatedMilestone);
    }
    @Test
    void delete_milestoneId_deletedMilestone() {
        milestoneService.delete(testMilestone.getId());
        verify(milestoneRepository, times(1)).deleteById(testMilestone.getId());
    }
}
