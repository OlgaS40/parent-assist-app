package com.parentapp.parent_assist.event_test;

import com.parentapp.parent_assist.children.Children;
import com.parentapp.parent_assist.test.Test;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class EventTest {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column
    private LocalDateTime date;

    @Column
    private String option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id_id", nullable = false)
    private Test testId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id_id", nullable = false)
    private Children childId;

}
