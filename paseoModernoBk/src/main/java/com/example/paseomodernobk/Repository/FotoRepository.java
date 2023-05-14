package com.example.paseomodernobk.Repository;

import com.example.paseomodernobk.Entity.FotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoRepository extends JpaRepository<FotoEntity, Long> {
    List<FotoEntity> findByProductoId(Long productoId);
}
