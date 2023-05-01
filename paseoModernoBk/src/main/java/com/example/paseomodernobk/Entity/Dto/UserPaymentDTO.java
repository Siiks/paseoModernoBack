package com.example.paseomodernobk.Entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserPaymentDTO {

    private Integer id;

    private Integer userId;

    private String paymentType;

    private String cardNumber;

    private Date cardExpiry;

    private String bankNumber;
}
