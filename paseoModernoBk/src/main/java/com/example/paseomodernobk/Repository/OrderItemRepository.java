package com.example.paseomodernobk.Repository;

import com.example.paseomodernobk.Entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    @Query("SELECT O FROM OrderItemEntity O " +
            "WHERE O.order.user.id = :id")
    List<OrderItemEntity> findByIdUser(@Param("id") Long id);
}
