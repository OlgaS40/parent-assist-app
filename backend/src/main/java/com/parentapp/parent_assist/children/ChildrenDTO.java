package com.parentapp.parent_assist.children;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChildrenDTO {

    @Size(max = 255)
    private String id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private Gender gender;

    @NotNull
    private LocalDate dateOfBirth;

    @Size(max = 255)
    private String imageUrl;

}
