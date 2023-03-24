package com.parentapp.test;

import com.parentapp.event_test.EventTest;
import com.parentapp.question.Question;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Test {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "questionTests")
    private Set<Question> testQuestions;

    @OneToMany(mappedBy = "test")
    private Set<EventTest> testEventTests;

}
