package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.CategoryEntity;
import com.example.paseomodernobk.Exceptions.ResourceNotFoundException;
import com.example.paseomodernobk.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<CategoryEntity> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public CategoryEntity createCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    public CategoryEntity updateCategory(CategoryEntity category) {
        CategoryEntity newCategory = getCategoryById(category.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la categoria"));

        newCategory.setId(category.getId());
        newCategory.setName(category.getName());

        return categoryRepository.save(category);
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}

