package com.parentapp.parent_assist.skills;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SkillsDTO {

    @Size(max = 255)
    private String id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String description;

}
