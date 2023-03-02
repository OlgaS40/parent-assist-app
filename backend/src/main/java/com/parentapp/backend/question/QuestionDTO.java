package com.parentapp.backend.question;

import com.parentapp.backend.activity.AgeUnit;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QuestionDTO {

    @Size(max = 255)
    private String id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    private AgeUnit ageUnit;

    @NotNull
    @Size(max = 255)
    private String ageFrom;

    @NotNull
    @Size(max = 255)
    private String ageTo;

    private TestOptionType answer;

    @NotNull
    @Size(max = 255)
    private String option1;

    @NotNull
    @Size(max = 255)
    private String option2;

    @Size(max = 255)
    private String option3;

    @Size(max = 255)
    private String option4;

    @Size(max = 255)
    private String option5;

    private List<String> testQuestions;

}
