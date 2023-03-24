package com.parentapp.fixture;

import com.parentapp.subscription.Subscription;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class SubscriptionFixture {
    private static final Subscription testSubscription1;
    private static final Subscription testSubscription2;

    static {
        testSubscription1 = Subscription.builder()
                .id("testSubscription01")
                .start(LocalDateTime.of(2022,11,20,0,0))
                .end(LocalDateTime.of(2023,2,20,0,0))
                .child(ChildFixture.testChild())
                .build();
        testSubscription2 = Subscription.builder()
                .id("testSubscription02")
                .start(LocalDateTime.of(2023,2,21,0,0))
                .end(LocalDateTime.of(2023,6,21,0,0))
                .child(ChildFixture.testChild())
                .build();
    }

    public static Subscription getTestSubscription() {
        return testSubscription1;
    }

    public static List<Subscription> testSubscriptions() {
        return Arrays.asList(testSubscription1, testSubscription2);
    }
}
