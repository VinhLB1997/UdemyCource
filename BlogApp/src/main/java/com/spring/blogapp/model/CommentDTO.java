package com.spring.blogapp.model;

import com.spring.blogapp.entity.Post;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDTO {

    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    @Email
    private String email;

    private String body;

    private Post post;
}
