package com.spring.blogapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class PostDTO {
    @JsonIgnore
    private Long id;

    @NotNull
    private String content;

    private String description;

    @NotNull
    private String title;

    private Long categoryId;
}
