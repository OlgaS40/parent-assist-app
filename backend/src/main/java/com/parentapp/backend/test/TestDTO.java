package com.parentapp.backend.test;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TestDTO {

    @Size(max = 255)
    private String id;

    @Size(max = 255)
    private String name;

}
