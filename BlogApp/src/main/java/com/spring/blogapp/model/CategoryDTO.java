package com.spring.blogapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CategoryDTO {

    @JsonIgnore
    private Long id;

    private String name;

    private String description;
}
