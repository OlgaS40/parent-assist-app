package com.parentapp.fixture;

import com.parentapp.skill.Skill;

import java.util.Arrays;
import java.util.List;

public class SkillFixture {
    private static final Skill testSkill1;
    private static final Skill testSkill2;

    static {
        testSkill1 = Skill.builder()
                .id("testSkill01")
                .name("Cognitive")
                .description("Learning, thinking, problem-solving")
                .build();
        testSkill2 = Skill.builder()
                .id("testSkill02")
                .name("Communication")
                .description("The abilities you use when giving and receiving different kinds of information")
                .build();
    }

    public static Skill getTestSkill() {
        return testSkill1;
    }

    public static List<Skill> testSkills() {
        return Arrays.asList(testSkill1, testSkill2);
    }
}
