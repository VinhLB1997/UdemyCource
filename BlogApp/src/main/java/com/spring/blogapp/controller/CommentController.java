package com.spring.blogapp.controller;

import com.spring.blogapp.model.CommentDTO;
import com.spring.blogapp.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/posts")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getAllCommentOfPost(@PathVariable(value = "postId") Long postId) {
        return new ResponseEntity<>(commentService.getAllCommentOfPost(postId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getAllCommentOfPost(@PathVariable(value = "postId") Long postId,
                                                                @PathVariable(value = "commentId") Long commentId) {
        return new ResponseEntity<>(commentService.getCommentOfPostById(postId, commentId), HttpStatus.OK);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(value = "postId") long postId,
                                                    @Valid @RequestBody CommentDTO CommentDTO){
        return new ResponseEntity<>(commentService.createCommentForPost(postId, CommentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "id") Long commentId,
                                                    @Valid @RequestBody CommentDTO commentDto){
        CommentDTO updatedComment = commentService.updateCommentOfPost(postId, commentId, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") Long commentId){
        commentService.deleteCommentOfPostById(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}
