package com.example.paseomodernobk.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "user_payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_expiry")
    private Date cardExpiry;

    @Column(name = "bank_number")
    private String bankNumber;

    // Constructors, getters and setters
}

