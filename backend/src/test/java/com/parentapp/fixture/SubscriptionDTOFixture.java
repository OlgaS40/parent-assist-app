package com.parentapp.fixture;

import com.parentapp.subscription.SubscriptionDTO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class SubscriptionDTOFixture {
    private static final SubscriptionDTO testSubscriptionDTO1;
    private static final SubscriptionDTO testSubscriptionDTO2;

    static {
        testSubscriptionDTO1 = SubscriptionDTO.builder()
                .id("testSubscription01")
                .start(LocalDateTime.of(2022, 11, 20, 0, 0))
                .end(LocalDateTime.of(2023, 2, 20, 0, 0))
                .childId(ChildFixture.testChild().getId())
                .build();
        testSubscriptionDTO2 = SubscriptionDTO.builder()
                .id("testSubscription02")
                .start(LocalDateTime.of(2023, 2, 21, 0, 0))
                .end(LocalDateTime.of(2023, 6, 21, 0, 0))
                .childId(ChildFixture.testChild().getId())
                .build();
    }

    public static SubscriptionDTO getTestSubscriptionDTO() {
        return testSubscriptionDTO1;
    }

    public static List<SubscriptionDTO> testSubscriptionDTOs() {
        return Arrays.asList(testSubscriptionDTO1, testSubscriptionDTO2);
    }
}
