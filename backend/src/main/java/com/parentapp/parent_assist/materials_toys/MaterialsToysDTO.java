package com.parentapp.parent_assist.materials_toys;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MaterialsToysDTO {

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
