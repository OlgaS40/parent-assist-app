package com.parentapp.parent_assist.relationships;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RelationshipsDTO {

    @Size(max = 255)
    private String id;

    @NotNull
    private Relationship relationship;

    @NotNull
    @Size(max = 255)
    private String parentId;

    @Size(max = 255)
    private String childId;

    @NotNull
    @Size(max = 255)
    private String planId;

}
