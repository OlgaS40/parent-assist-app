package com.parentapp.backend.fixture;

import com.parentapp.backend.child.ChildDTO;
import com.parentapp.backend.child.Gender;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ChildDTOFixture {
    private static final ChildDTO testChildDTO1;
    private static final ChildDTO testChildDTO2;

    static {
        testChildDTO1 = ChildDTO.builder()
                .id("testChild01")
                .name("Alex")
                .gender(Gender.BOY)
                .dateOfBirth(LocalDate.of(2019, 7, 20))
                .build();
        testChildDTO2 = ChildDTO.builder()
                .id("testChild02")
                .name("Anisia")
                .gender(Gender.GIRL)
                .dateOfBirth(LocalDate.of(2019, 3, 8))
                .build();
    }
    public static ChildDTO testChild(){
        return testChildDTO1;
    }
    public static List<ChildDTO> testChildDTOs(){
        return Arrays.asList(testChildDTO1, testChildDTO2);
    }
}
