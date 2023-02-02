package com.parentapp.parent_assist.parents;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ParentsDTO {

    @Size(max = 255)
    private String id;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String surname;

    private LocalDate dateOfBirth;

    @Size(max = 255)
    private String language;

    @Size(max = 255)
    private String country;

    @Size(max = 255)
    private String city;

    @Size(max = 255)
    private String address;

    @Size(max = 255)
    private String postalCode;

}
