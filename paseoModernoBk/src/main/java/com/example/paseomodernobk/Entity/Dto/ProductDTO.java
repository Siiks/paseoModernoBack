package com.example.paseomodernobk.Entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private Long idCategory;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer inventoryQuantity;
}

