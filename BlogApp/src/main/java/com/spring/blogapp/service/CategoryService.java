package com.spring.blogapp.service;

import com.spring.blogapp.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

    String deleteCategory(Long categoryId);

    CategoryDTO getDetaiCategory(Long categoryId);

    List<CategoryDTO> getAllCategory();

}
