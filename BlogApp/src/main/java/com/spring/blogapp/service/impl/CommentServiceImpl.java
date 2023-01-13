package com.spring.blogapp.service.impl;

import com.spring.blogapp.entity.Comment;
import com.spring.blogapp.entity.Post;
import com.spring.blogapp.exception.BlogAPIException;
import com.spring.blogapp.exception.DataNotFoundException;
import com.spring.blogapp.model.CommentDTO;
import com.spring.blogapp.repository.CommentRepository;
import com.spring.blogapp.repository.PostRepository;
import com.spring.blogapp.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public List<CommentDTO> getAllCommentOfPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("Post", "id", String.valueOf(postId)));
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentOfPostById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("Post", "id", String.valueOf(postId)));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new DataNotFoundException("Comment", "id", String.valueOf(commentId)));
        if(!comment.getPost().getId().equals(post.getId())){
           throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment do not belong to post");
        }
        return mapToDTO(comment);
    }

    @Override
    public CommentDTO createCommentForPost(Long postId, CommentDTO commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("Post", "id", String.valueOf(postId)));
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        commentRepository.save(comment);
        return mapToDTO(comment);
    }

    @Override
    public CommentDTO updateCommentOfPost(Long postId, Long commentId, CommentDTO commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("Post", "id", String.valueOf(postId)));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new DataNotFoundException("Comment", "id", String.valueOf(commentId)));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment do not belong to post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteCommentOfPostById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new DataNotFoundException("Post", "id", String.valueOf(postId)));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new DataNotFoundException("Comment", "id", String.valueOf(commentId)));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        commentRepository.delete(comment);
    }

    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO response = new CommentDTO();
        BeanUtils.copyProperties(comment, response);
        return response;
    }
    private Comment mapToEntity(CommentDTO dto) {
        Comment response = new Comment();
        BeanUtils.copyProperties(dto, response);
        return response;
    }

    private Comment mapToEntity(CommentDTO dto, Comment post) {
        BeanUtils.copyProperties(dto, post);
        return post;
    }

}
