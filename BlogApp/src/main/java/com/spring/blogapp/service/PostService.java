package com.spring.blogapp.service;

import com.spring.blogapp.model.PostDTO;
import com.spring.blogapp.response.PostPaginationResponse;

public interface PostService {

    PostDTO createPost(PostDTO param);
    PostDTO findPostById(Long id);
    PostPaginationResponse findAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDTO updatePost(Long id, PostDTO param);
    void deletePost(Long id);
}
