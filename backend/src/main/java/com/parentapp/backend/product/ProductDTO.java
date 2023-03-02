package com.parentapp.backend.product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
public class ProductDTO {
    private String id;
    private String name;
    private String description;
    private LocalDate createDate;
    private Set<String> planIdList;
}
