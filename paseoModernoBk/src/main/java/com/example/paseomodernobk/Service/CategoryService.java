package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.CategoryEntity;
import com.example.paseomodernobk.Entity.ProductEntity;
import com.example.paseomodernobk.Exceptions.ResourceNotFoundException;
import com.example.paseomodernobk.Repository.CategoryRepository;
import com.example.paseomodernobk.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Page<CategoryEntity> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
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
        final Long idSinCategoria = Long.valueOf(1);
        CategoryEntity category = categoryRepository.findById(idSinCategoria).orElse(null);

        if (category != null) {
            List<ProductEntity> productList = productRepository.findAllByCategory(id);

            productList.forEach(productEntity -> {
                productEntity.setCategory(category);
            });
            productRepository.saveAll(productList);
            categoryRepository.deleteById(id);
        }
    }

}

