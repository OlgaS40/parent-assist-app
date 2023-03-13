package com.parentapp.backend.fixture;

import com.parentapp.backend.activity.AgeUnit;
import com.parentapp.backend.milestone.Milestone;

import java.util.Arrays;
import java.util.List;

public class MilestoneFixture {
    private static final Milestone testMilestone1;
    private static final Milestone testMilestone2;

    static {
        testMilestone1 = Milestone.builder()
                .id("testMilestone01")
                .name("Talks with you in conversation")
                .description("Talks with you in conversation using at least two back-and-forth exchanges")
                .ageUnit(AgeUnit.YEARS)
                .ageFrom(3)
                .ageTo(3)
                .build();
        testMilestone2 = Milestone.builder()
                .id("testMilestone02")
                .name("Says what action is happening")
                .description("Says what action is happening in a picture or book when asked, like “running,” “eating,” or “playing”")
                .ageUnit(AgeUnit.YEARS)
                .ageFrom(3)
                .ageTo(3)
                .build();
    }

    public static Milestone getTestMilestone() {
        return testMilestone1;
    }

    public static List<Milestone> testMilestones() {
        return Arrays.asList(testMilestone1, testMilestone2);
    }
}
