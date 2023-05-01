package com.example.paseomodernobk.Entity.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordForgotConfirmDto {
    @NotBlank
    String token;
}