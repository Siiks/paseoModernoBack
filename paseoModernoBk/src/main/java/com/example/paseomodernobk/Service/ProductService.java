package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.FotoEntity;
import com.example.paseomodernobk.Entity.ProductEntity;
import com.example.paseomodernobk.Exceptions.ResourceNotFoundException;
import com.example.paseomodernobk.Repository.CartItemRepository;
import com.example.paseomodernobk.Repository.ProductRepository;
import com.example.paseomodernobk.Utils.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<ProductEntity> getAllProductsFiltered(Long idCategoria, String nombre, Pageable pageable) {
        Page<ProductEntity> page = productRepository.findAllByParams(idCategoria, nombre, pageable);
        List<ProductEntity> listaProducto = page.getContent();

        listaProducto.forEach(producto -> {
            List<FotoEntity> fotos = producto.getFotos(); // Obtén la lista de fotos del producto

            if (!fotos.isEmpty()) {
                fotos.forEach(foto -> {
                    foto.setImage(ImageUtility.decompressImage(foto.getImage())); // Descomprime la imagen individualmente
                });
            }

            producto.setFotos(fotos); // Asigna la lista de fotos actualizada al producto
        });

        page = new PageImpl<>(listaProducto, pageable, page.getTotalElements()); // Actualiza la lista de productos en la página

        // Devuelve la página con los productos filtrados y las imágenes actualizadas
        return page;
    }

    public ProductEntity getProductById(Long id) throws ResourceNotFoundException {
        ProductEntity product = productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (!product.getFotos().isEmpty()){
            product.getFotos().forEach(foto -> {
                foto.setImage(ImageUtility.decompressImage(foto.getImage()));
            });
        }
        return product;
    }

    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    public ProductEntity updateProduct(ProductEntity product) throws ResourceNotFoundException {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) throws ResourceNotFoundException {
        ProductEntity product = getProductById(id);
        productRepository.delete(product);
    }
}

