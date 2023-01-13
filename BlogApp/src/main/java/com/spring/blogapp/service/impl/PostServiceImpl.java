package com.spring.blogapp.service.impl;

import com.spring.blogapp.entity.Post;
import com.spring.blogapp.exception.DataNotFoundException;
import com.spring.blogapp.model.PostDTO;
import com.spring.blogapp.repository.PostRepository;
import com.spring.blogapp.response.PostPaginationResponse;
import com.spring.blogapp.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Override
    public PostDTO createPost(PostDTO param) {
        Post post = mapToEntity(param);
        postRepository.save(post);
        return mapToDTO(post);
    }

    @Override
    public PostDTO findPostById(Long id) {
        return postRepository.findById(id).map(this::mapToDTO).orElseThrow(() -> new DataNotFoundException("Post", "id", String.valueOf(id)));
    }

    @Override
    public PostPaginationResponse findAllPost(int pageNo,int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable page = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePost = postRepository.findAll(page);
        List<PostDTO> listOfPost = pagePost.getContent().stream().map(this::mapToDTO).collect(Collectors.toList());
        PostPaginationResponse postPaginationResponse = new PostPaginationResponse();
        postPaginationResponse.setContent(listOfPost);
        postPaginationResponse.setPageNo(pagePost.getNumber());
        postPaginationResponse.setPageSize(pagePost.getSize());
        postPaginationResponse.setTotalElement(pagePost.getTotalElements());
        postPaginationResponse.setTotalPage(pagePost.getTotalPages());
        postPaginationResponse.setLastPage(pagePost.isLast());
        return postPaginationResponse;
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO param) {
        Post post = postRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Post", "id", String.valueOf(id)));
        mapToEntity(param, post);
        post.setId(id);
        return mapToDTO(postRepository.save(post));
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Post", "id", String.valueOf(id)));
        postRepository.delete(post);
    }


    private PostDTO mapToDTO(Post post) {
        PostDTO response = new PostDTO();
        BeanUtils.copyProperties(post, response);
        return response;
    }

    private Post mapToEntity(PostDTO dto) {
        Post response = new Post();
        BeanUtils.copyProperties(dto, response);
        return response;
    }

    private Post mapToEntity(PostDTO dto, Post post) {
        BeanUtils.copyProperties(dto, post);
        return post;
    }
}
