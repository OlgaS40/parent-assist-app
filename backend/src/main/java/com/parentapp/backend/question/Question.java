package com.parentapp.backend.question;

import com.parentapp.backend.activity.AgeUnit;
import com.parentapp.backend.test.Test;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Question {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AgeUnit ageUnit;

    @Column(nullable = false)
    private String ageFrom;

    @Column(nullable = false)
    private String ageTo;

    @Column
    @Enumerated(EnumType.STRING)
    private TestOptionType answer;

    @Column(nullable = false)
    private String option1;

    @Column(nullable = false)
    private String option2;

    @Column
    private String option3;

    @Column
    private String option4;

    @Column
    private String option5;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "test_question",
            joinColumns = @JoinColumn(name = "questions_id"),
            inverseJoinColumns = @JoinColumn(name = "test_id")
    )
    private Set<Test> questionTests;

}
