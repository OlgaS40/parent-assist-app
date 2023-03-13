package com.parentapp.backend.services;

import com.parentapp.backend.fixture.SkillDTOFixture;
import com.parentapp.backend.fixture.SkillFixture;
import com.parentapp.backend.skill.Skill;
import com.parentapp.backend.skill.SkillDTO;
import com.parentapp.backend.skill.SkillRepository;
import com.parentapp.backend.skill.SkillService;
import com.parentapp.backend.util.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {
    @Mock
    private SkillRepository skillRepository;
    @InjectMocks
    private SkillService skillService;
    private Skill testSkill;
    private SkillDTO testSkillDTO;
    private List<Skill> testSkills;
    private List<SkillDTO> testSkillDTOs;
    @BeforeEach
    public void setup() {
        testSkill = SkillFixture.getTestSkill();
        testSkillDTO = SkillDTOFixture.getTestSkillDTO();
        testSkills = SkillFixture.testSkills();
        testSkillDTOs = SkillDTOFixture.testSkillDTOs();
    }
    @Test
    void findAll_whenInvoked_returnsAListWithTwoSkills() {
        when(skillRepository.findAll()).thenReturn(testSkills);
        assertThat(skillService.findAll())
                .isNotEmpty()
                .containsExactlyInAnyOrderElementsOf(testSkillDTOs);
    }
    @Test
    void get_skillId_returnsSkillDTO() {
        when(skillRepository.findById(anyString())).thenReturn(Optional.ofNullable(testSkill));

        SkillDTO skillDTO = skillService.get("testSkill01");
        assertThat(skillDTO).isNotNull();
        assertThat(skillDTO).isEqualTo(testSkillDTO);
    }
    @Test
    void get_NonExistentId_throwNotFoundException() {
        when(skillRepository.findById(anyString())).thenReturn(Optional.empty());
        String id = testSkill.getId();

        assertThrows(NotFoundException.class, () -> skillService.get(id));
        verify(skillRepository, times(1)).findById(id);
    }
    @Test
    void create_whenInvoked_returnsSkillId() {
        when(skillRepository.save(any(Skill.class))).thenReturn(testSkill);
        String createdSkillId = skillService.create(testSkillDTO);

        verify(skillRepository, times(1)).save(any(Skill.class));
        assertThat(createdSkillId).isNotNull();
        assertThat(createdSkillId).isEqualTo(testSkill.getId());
    }
    @Test
    void update_skillIdAndDTO_updatedSkill() {
        SkillDTO updatedSkillDTO = testSkillDTO;

        String id = updatedSkillDTO.getId();
        updatedSkillDTO.setDescription("Learning, thinking, problem-solving, logic");
        when(skillRepository.findById(anyString())).thenReturn(Optional.of(testSkill));

        Skill updatedMilestone = testSkill;
        updatedMilestone.setDescription("Learning, thinking, problem-solving, logic");


        skillService.update(id, updatedSkillDTO);

        verify(skillRepository, times(1)).findById(id);
        verify(skillRepository, times(1)).save(updatedMilestone);
    }
    @Test
    void update_NonExistentSkill_throwsNotFoundException() {
        when(skillRepository.findById(anyString())).thenReturn(Optional.empty());

        SkillDTO updatedSkillDTO = testSkillDTO;
        String id = updatedSkillDTO.getId();
        Skill updatedSkill = testSkill;

        assertThrows(NotFoundException.class, () -> skillService.update(id, updatedSkillDTO));

        verify(skillRepository, times(1)).findById(id);
        verify(skillRepository, times(0)).save(updatedSkill);
    }
    @Test
    void delete_skillId_deletedSkill() {
        skillService.delete(testSkill.getId());
        verify(skillRepository, times(1)).deleteById(testSkill.getId());
    }
}
