package com.example.paseomodernobk.Entity.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordForgotDto {

    @NotBlank
    @Size(min = 3, max = 52)
    private String email;
}
