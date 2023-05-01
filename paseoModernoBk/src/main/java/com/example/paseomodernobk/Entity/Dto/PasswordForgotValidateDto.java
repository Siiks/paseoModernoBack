package com.example.paseomodernobk.Entity.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordForgotValidateDto {

    @NotBlank
    private String token;

    @NotBlank
    @Size(min = 6, max = 52)
    private String newPassword;

    @NotBlank
    private String newPasswordConfirm;
}
