package com.example.paseomodernobk.Controller;

import com.example.paseomodernobk.Entity.AuthenticationResponse;
import com.example.paseomodernobk.Entity.AuthenticationRequest;
import com.example.paseomodernobk.Entity.Dto.PasswordForgotConfirmDto;
import com.example.paseomodernobk.Entity.Dto.PasswordForgotDto;
import com.example.paseomodernobk.Entity.Dto.PasswordForgotValidateDto;
import com.example.paseomodernobk.Entity.RegisterRequest;
import com.example.paseomodernobk.Entity.UserEntity;
import com.example.paseomodernobk.Service.AuthenticationService;
import com.example.paseomodernobk.Service.TokenService;
import com.example.paseomodernobk.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final TokenService tokenService;

    @Autowired
    private final ModelMapper modelMapper;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
            @RequestBody RegisterRequest request
    ){
        AuthenticationResponse authenticationResponse =
        authenticationService.register(request);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register (
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping(value = "/account/registration/validate")
    public ResponseEntity<HttpStatus> validateEmail(@RequestBody @Valid AuthenticationResponse validateEmail) {
        tokenService.validateEmail(validateEmail.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/password/forgot")
    public ResponseEntity<HttpStatus> forgotPasswordRequest(@RequestBody @Valid PasswordForgotDto passwordForgotRequest) {
        tokenService.createPasswordResetToken(passwordForgotRequest.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/forgot/validate")
    public ResponseEntity<HttpStatus> validateForgotPassword(@RequestBody @Valid PasswordForgotValidateDto passwordForgotValidateDto) {
        tokenService.validateForgotPassword(passwordForgotValidateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/forgot/confirm")
    public ResponseEntity<HttpStatus> confirmForgotPassword(@RequestBody @Valid PasswordForgotConfirmDto passwordForgotConfirmDto) {
        tokenService.validateForgotPasswordConfirm(passwordForgotConfirmDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<String> confirmForgotPassword() {
        authenticationService.logout();
        return new ResponseEntity<>("Te has salido con exito", HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<Boolean> protectedResource(HttpServletRequest request) {
        if (authenticationService.isAuthenticated(request)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }
}
