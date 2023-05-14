package com.example.paseomodernobk.Controller;

import com.example.paseomodernobk.Entity.Dto.ProductDTO;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FotoStorageService fotoStorageService;

    @GetMapping
    public Page<ProductEntity> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long id) {
        try {
            ProductEntity product = productService.getProductById(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("add")
    public ProductEntity createProduct(@RequestBody ProductDTO product) {
        return productService.createProduct(product);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductEntity> updateProduct(@RequestBody ProductDTO product) {
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
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/{productoId}/fotos")
    public ResponseEntity<?> crearFoto(@PathVariable Long productoId, @RequestParam("archivo") MultipartFile archivo) {
        String nombreArchivo = this.fotoStorageService.almacenarArchivo(archivo, productoId);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/productos/{productoId}/fotos/{nombreArchivo}")
                .buildAndExpand(productoId, nombreArchivo)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{productoId}/fotos/{nombreArchivo}")
    public ResponseEntity<?> borrarFoto(@PathVariable Long productoId, @PathVariable String nombreArchivo) {
        this.fotoStorageService.borrarArchivo(nombreArchivo);
        return ResponseEntity.noContent().build();
    }

}
