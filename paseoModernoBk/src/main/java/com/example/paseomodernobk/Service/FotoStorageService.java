package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.Dto.MessageResponse;
import com.example.paseomodernobk.Entity.Dto.ImageData;
import com.example.paseomodernobk.Entity.FotoEntity;
import com.example.paseomodernobk.Entity.ImageUploadResponse;
import com.example.paseomodernobk.Exceptions.ResourceNotFoundException;
import com.example.paseomodernobk.Repository.FotoRepository;
import com.example.paseomodernobk.Repository.ProductRepository;
import com.example.paseomodernobk.Utils.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FotoStorageService {

    private Path directorioFotos = Paths.get("fotos").toAbsolutePath().normalize();

    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private ProductRepository productRepository;


    public ImageUploadResponse uploadImage(Long productId, MultipartFile file)
            throws IOException {

        fotoRepository.save(FotoEntity.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .producto(
                        productRepository.findById(productId).orElseThrow(ResourceNotFoundException::new)
                )
                .image(ImageUtility.compressImage(file.getBytes())).build());
        return (new ImageUploadResponse("Image uploaded successfully: " +
                        file.getOriginalFilename()));
    }

    public List<ImageData> viewImages(Long productId) throws IOException {
        final List<FotoEntity> dbImage = fotoRepository.findAllByProductoId(productId);

        return dbImage.stream()
                .map(fotoEntity -> new ImageData(fotoEntity.getId(), ImageUtility.decompressImage(fotoEntity.getImage())))
                .collect(Collectors.toList());
    }

    public MessageResponse deleteImage(Long id) {
        fotoRepository.deleteById(id);
        MessageResponse mensaje = new MessageResponse();
        mensaje.setMessage("La imagen se ha borrado con exito");
        return mensaje;
    }
}

