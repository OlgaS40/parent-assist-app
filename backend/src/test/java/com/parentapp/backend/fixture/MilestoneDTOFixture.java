package com.parentapp.backend.fixture;

import com.parentapp.backend.activity.AgeUnit;
import com.parentapp.backend.milestone.MilestoneDTO;

import java.util.Arrays;
import java.util.List;

public class MilestoneDTOFixture {
    private static final MilestoneDTO testMilestoneDTO1;
    private static final MilestoneDTO testMilestoneDTO2;

    static {
        testMilestoneDTO1 = MilestoneDTO.builder()
                .id("testMilestone01")
                .name("Talks with you in conversation")
                .description("Talks with you in conversation using at least two back-and-forth exchanges")
                .ageUnit(AgeUnit.YEARS)
                .ageFrom(3)
                .ageTo(3)
                .build();
        testMilestoneDTO2 = MilestoneDTO.builder()
                .id("testMilestone02")
                .name("Says what action is happening")
                .description("Says what action is happening in a picture or book when asked, like “running,” “eating,” or “playing”")
                .ageUnit(AgeUnit.YEARS)
                .ageFrom(3)
                .ageTo(3)
                .build();
    }

    public static MilestoneDTO getTestMilestoneDTO() {
        return testMilestoneDTO1;
    }

    public static List<MilestoneDTO> testMilestoneDTOs() {
        return Arrays.asList(testMilestoneDTO1, testMilestoneDTO2);
    }
}
