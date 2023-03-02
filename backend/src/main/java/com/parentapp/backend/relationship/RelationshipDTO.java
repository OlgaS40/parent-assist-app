package com.parentapp.backend.relationship;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RelationshipDTO {

    @Size(max = 255)
    private String id;

    @NotNull
    private RelationshipEnum relationshipEnum;

    @NotNull
    @Size(max = 255)
    private String parentId;

    @Size(max = 255)
    private String childId;

    @NotNull
    @Size(max = 255)
    private String planId;

}
