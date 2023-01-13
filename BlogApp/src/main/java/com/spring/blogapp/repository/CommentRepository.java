package com.spring.blogapp.repository;

import com.spring.blogapp.entity.Comment;
import com.spring.blogapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}
