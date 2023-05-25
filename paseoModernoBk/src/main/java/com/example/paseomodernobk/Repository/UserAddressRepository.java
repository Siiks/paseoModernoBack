package com.example.paseomodernobk.Repository;

import com.example.paseomodernobk.Entity.OrderEntity;
import com.example.paseomodernobk.Entity.UserAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddressEntity, Long> {

    @Query("SELECT A FROM UserAddressEntity A " +
            "WHERE A.user.id = :id")
    List<UserAddressEntity> findAllByUserId(@Param("id") Long id);
}
