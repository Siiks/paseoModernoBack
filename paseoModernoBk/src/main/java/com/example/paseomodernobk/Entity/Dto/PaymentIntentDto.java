package com.example.paseomodernobk.Entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentIntentDto {
    private enum Currency{
        USD, EUR;
    }
    private String description;
    private int amount;
    private Currency currency;
}
