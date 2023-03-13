package com.parentapp.backend.fixture;

import com.parentapp.backend.child.Child;
import com.parentapp.backend.child.Gender;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ChildFixture {
    private static final Child testChild1;
    private static final Child testChild2;

    static {
        testChild1 = Child.builder()
                .id("testChild01")
                .name("Alex")
                .gender(Gender.BOY)
                .dateOfBirth(LocalDate.of(2019, 7, 20))
                .build();
        testChild2 = Child.builder()
                .id("testChild02")
                .name("Anisia")
                .gender(Gender.GIRL)
                .dateOfBirth(LocalDate.of(2019, 3, 8))
                .build();
    }
    public static Child testChild(){
        return testChild1;
    }
    public static List<Child> testChildren(){
        return Arrays.asList(testChild1, testChild2);
    }
}
