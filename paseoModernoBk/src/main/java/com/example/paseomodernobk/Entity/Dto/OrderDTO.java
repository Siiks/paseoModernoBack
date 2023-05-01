package com.example.paseomodernobk.Entity.Dto;

import com.example.paseomodernobk.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    private UserEntity user;

    private BigDecimal total;

    private String status;

    private LocalDateTime orderDate;
}
