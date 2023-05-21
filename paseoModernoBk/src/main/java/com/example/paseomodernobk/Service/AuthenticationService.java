package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.AuthenticationRequest;
import com.example.paseomodernobk.Entity.AuthenticationResponse;
import com.example.paseomodernobk.Entity.RegisterRequest;
import com.example.paseomodernobk.Entity.UserEntity;
import com.example.paseomodernobk.Enum.Role;
import com.example.paseomodernobk.Repository.UserRepository;
import com.example.paseomodernobk.Security.JwtService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    ModelMapper modelMapper;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(
                    passwordEncoder.encode(request.getPassword())
                )
                .role(Role.USER)
                .build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(user, user.getRole());

        sendEmail(user);
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void sendEmail(UserEntity user){
        tokenService.createEmailConfirmToken(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user, user.getRole());

        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public String logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
            return "Te has salido correctamente";
        }
        return "Hubo un problema al salirte";
    }

}
