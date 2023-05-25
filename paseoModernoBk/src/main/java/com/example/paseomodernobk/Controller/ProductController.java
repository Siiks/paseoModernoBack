package com.example.paseomodernobk.Controller;

import com.example.paseomodernobk.Entity.Dto.MessageResponse;
import com.example.paseomodernobk.Entity.Dto.ImageData;
import com.example.paseomodernobk.Entity.ProductEntity;
import com.example.paseomodernobk.Exceptions.ResourceNotFoundException;
import com.example.paseomodernobk.Service.FotoStorageService;
import com.example.paseomodernobk.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FotoStorageService fotoStorageService;

    @GetMapping("")
    public Page<ProductEntity> getAllProducts(
            @RequestParam(required = false) Long idCategoria,
            @RequestParam(required = false) String nombre,
            Pageable pageable) {
        return productService.getAllProductsFiltered(idCategoria, nombre, pageable);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long id) {
        try {
            ProductEntity product = productService.getProductById(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("add")
    public ProductEntity createProduct(@RequestBody ProductEntity product) {
        return productService.createProduct(product);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductEntity> updateProduct(@RequestBody ProductEntity product) {
        try {
            ProductEntity updatedProduct = productService.updateProduct(product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/{productoId}/fotos")
    public ResponseEntity<?> crearFoto(@PathVariable Long productoId, @RequestParam("image") MultipartFile archivo) throws IOException {
        return new ResponseEntity<>(this.fotoStorageService.uploadImage(productoId, archivo), HttpStatus.OK);
    }

    @GetMapping("/image/info/{id}")
    public  ResponseEntity<List<ImageData>> getImagesByProducto(@PathVariable("id") Long id) throws IOException {
        return new ResponseEntity<>(this.fotoStorageService.viewImages(id), HttpStatus.OK);
    }

    @DeleteMapping("/image/delete/{id}")
    public ResponseEntity<MessageResponse> deleteImageById(@PathVariable Long id){
        return new ResponseEntity<>(this.fotoStorageService.deleteImage(id), HttpStatus.OK);
    }
}
