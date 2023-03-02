package com.parentapp.backend.material_toy;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MaterialToyDTO {

    @Size(max = 255)
    private String id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String category;

    private String description;

    @Size(max = 255)
    private String imageUrl;

}
