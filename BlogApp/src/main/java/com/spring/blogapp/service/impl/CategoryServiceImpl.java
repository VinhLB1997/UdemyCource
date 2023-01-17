package com.spring.blogapp.service.impl;

import com.spring.blogapp.entity.Category;
import com.spring.blogapp.exception.DataNotFoundException;
import com.spring.blogapp.model.CategoryDTO;
import com.spring.blogapp.repository.CategoryRepository;
import com.spring.blogapp.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        return mapToDTO(categoryRepository.save(mapToEntity(categoryDTO)));
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new DataNotFoundException("Category","id", String.valueOf(categoryId)));
        categoryDTO.setId(category.getId());
        modelMapper.map(categoryDTO,category);
        return mapToDTO(categoryRepository.save(category));
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new DataNotFoundException("Category","id", String.valueOf(categoryId)));
        categoryRepository.delete(category);
        return "Delete Success";
    }

    @Override
    public CategoryDTO getDetaiCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new DataNotFoundException("Category","id", String.valueOf(categoryId)));
        return mapToDTO(category);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private Category mapToEntity(CategoryDTO dto){
        Category entity = new Category();
        modelMapper.map(dto,entity);
        return entity;
    }

    private CategoryDTO mapToDTO(Category entity){
        CategoryDTO dto = new CategoryDTO();
        modelMapper.map(entity,dto);
        return dto;
    }
}
