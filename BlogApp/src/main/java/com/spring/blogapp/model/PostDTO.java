package com.spring.blogapp.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class PostDTO {

    private Long id;

    @NotNull
    private String content;

    private String description;

    @NotNull
    private String title;
}
