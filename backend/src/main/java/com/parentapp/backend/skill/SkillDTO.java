package com.parentapp.backend.skill;

import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Getter
@Setter
public class SkillDTO {

    @Size(max = 255)
    private String id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String description;

}
