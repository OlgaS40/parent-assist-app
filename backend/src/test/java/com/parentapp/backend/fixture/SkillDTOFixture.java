package com.parentapp.backend.fixture;

import com.parentapp.backend.skill.SkillDTO;

import java.util.Arrays;
import java.util.List;

public class SkillDTOFixture {
    private static final SkillDTO testSkillDTO1;
    private static final SkillDTO testSkillDTO2;

    static {
        testSkillDTO1 = SkillDTO.builder()
                .id("testSkill01")
                .name("Cognitive")
                .description("Learning, thinking, problem-solving")
                .build();
        testSkillDTO2 = SkillDTO.builder()
                .id("testSkill02")
                .name("Communication")
                .description("The abilities you use when giving and receiving different kinds of information")
                .build();
    }

    public static SkillDTO getTestSkillDTO() {
        return testSkillDTO1;
    }

    public static List<SkillDTO> testSkillDTOs() {
        return Arrays.asList(testSkillDTO1, testSkillDTO2);
    }
}
