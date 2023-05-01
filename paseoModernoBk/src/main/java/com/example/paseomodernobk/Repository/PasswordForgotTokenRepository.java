package com.example.paseomodernobk.Repository;

import com.example.paseomodernobk.Entity.PasswordForgotToken;
import com.example.paseomodernobk.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordForgotTokenRepository extends JpaRepository<PasswordForgotToken, Long> {
    Optional<PasswordForgotToken> findByToken(String token);

    Optional<PasswordForgotToken> findByUser(UserEntity user);
}
