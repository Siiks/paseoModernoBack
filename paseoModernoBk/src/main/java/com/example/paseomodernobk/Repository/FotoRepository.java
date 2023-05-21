package com.example.paseomodernobk.Repository;

import com.example.paseomodernobk.Entity.FotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface FotoRepository extends JpaRepository<FotoEntity, Long> {
    List<FotoEntity> findAllByProductoId(Long productId);
}
