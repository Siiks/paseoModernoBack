package com.example.paseomodernobk.Repository;


import com.example.paseomodernobk.Entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT P FROM ProductEntity P " +
            "WHERE P.category.id = :idCategoria")
    List<ProductEntity> findAllByCategory(Long idCategoria);

    @Query("SELECT P FROM ProductEntity P " +
            "WHERE (:idCategory IS NULL OR P.category.id = :idCategory) " +
            "AND (:nombre IS NULL OR P.name LIKE CONCAT('%', :nombre, '%')) " +
            "AND (:nombre IS NULL OR P.description LIKE CONCAT('%', :nombre, '%'))")
    Page<ProductEntity> findAllByParams(@Param("idCategory") Long idCategory,
                                        @Param("nombre") String nombre,
                                        Pageable pageable);

}
