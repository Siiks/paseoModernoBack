package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.Dto.ProductDTO;
import com.example.paseomodernobk.Entity.ProductEntity;
import com.example.paseomodernobk.Exceptions.ResourceNotFoundException;
import com.example.paseomodernobk.Repository.CategoryRepository;
import com.example.paseomodernobk.Repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<ProductEntity> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public ProductEntity getProductById(Long id) throws ResourceNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + id));
    }

    public ProductEntity createProduct(ProductDTO product) {
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        productEntity.setCategory(categoryRepository.findById(product.getIdCategory()).get());
        return productRepository.save(productEntity);
    }

    public ProductEntity updateProduct(ProductDTO product) throws ResourceNotFoundException {
        ProductEntity existingProduct = modelMapper.map(product, ProductEntity.class);
        existingProduct.setCategory(categoryRepository.findById(product.getIdCategory()).get());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) throws ResourceNotFoundException {
        ProductEntity product = getProductById(id);
        productRepository.delete(product);
    }
}

