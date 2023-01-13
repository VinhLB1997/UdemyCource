package com.spring.blogapp.service;

import com.spring.blogapp.model.CommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getAllCommentOfPost(Long postId);
    CommentDTO getCommentOfPostById(Long postId, Long commentId);
    CommentDTO createCommentForPost(Long postId, CommentDTO commentDto);
    CommentDTO updateCommentOfPost(Long postId, Long commentId, CommentDTO commentDto);
    void deleteCommentOfPostById(Long postId, Long commentId);
}
