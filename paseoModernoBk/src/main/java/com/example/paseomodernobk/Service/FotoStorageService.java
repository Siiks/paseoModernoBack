package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.FotoEntity;
import com.example.paseomodernobk.Entity.ProductEntity;
import com.example.paseomodernobk.Exceptions.InvalidArgumentException;
import com.example.paseomodernobk.Exceptions.ResourceNotFoundException;
import com.example.paseomodernobk.Repository.FotoRepository;
import com.example.paseomodernobk.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

@Service
public class FotoStorageService {

    private Path directorioFotos = Paths.get("fotos").toAbsolutePath().normalize();

    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private ProductRepository productRepository;

    public String almacenarArchivo(MultipartFile archivo, Long productoId) {
        String nombreArchivo = StringUtils.cleanPath(Objects.requireNonNull(archivo.getOriginalFilename()));

        try {
            if (nombreArchivo.contains("..")) {
                throw new RuntimeException("El nombre del archivo contiene una secuencia de ruta inválida");
            }
            ProductEntity productEntity = productRepository.findById(productoId).orElseThrow(ResourceNotFoundException::new);
            Path archivoPath = this.directorioFotos.resolve(nombreArchivo);
            Files.copy(archivo.getInputStream(), archivoPath, StandardCopyOption.REPLACE_EXISTING);

            FotoEntity foto = new FotoEntity();
            foto.setFileName(nombreArchivo);
            foto.setProducto(productEntity);
            this.fotoRepository.save(foto);

            return nombreArchivo;
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo almacenar el archivo " + nombreArchivo, ex);
        }
    }

    public void borrarArchivo(String nombreArchivo) {
        try {
            Path archivoPath = this.directorioFotos.resolve(nombreArchivo).normalize();
            Files.deleteIfExists(archivoPath);
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo borrar el archivo " + nombreArchivo, ex);
        }
    }
}

