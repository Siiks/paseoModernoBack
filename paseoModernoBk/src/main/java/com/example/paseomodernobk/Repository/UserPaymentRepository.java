package com.example.paseomodernobk.Repository;

import com.example.paseomodernobk.Entity.OrderEntity;
import com.example.paseomodernobk.Entity.UserPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPaymentEntity, Long> {
}
