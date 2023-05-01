package com.example.paseomodernobk.Entity.Dto;

import com.example.paseomodernobk.Entity.ProductEntity;
import com.example.paseomodernobk.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;

}
