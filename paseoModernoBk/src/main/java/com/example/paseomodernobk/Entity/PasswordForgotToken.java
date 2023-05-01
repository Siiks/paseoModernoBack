package com.example.paseomodernobk.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "password_reset_token")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PasswordForgotToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    @Column(name = "expiry_date")
    private Date expiryDate;

}