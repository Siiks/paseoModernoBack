package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.Dto.PasswordForgotValidateDto;
import com.example.paseomodernobk.Entity.PasswordForgotToken;
import com.example.paseomodernobk.Entity.UserEntity;
import com.example.paseomodernobk.Entity.VerificationToken;
import com.example.paseomodernobk.Events.OnPasswordForgotRequestEvent;
import com.example.paseomodernobk.Events.OnRegistrationCompleteEvent;
import com.example.paseomodernobk.Exceptions.InvalidArgumentException;
import com.example.paseomodernobk.Exceptions.ResourceNotFoundException;
import com.example.paseomodernobk.Repository.PasswordForgotTokenRepository;
import com.example.paseomodernobk.Repository.VerificationTokenRepository;
import com.example.paseomodernobk.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final int EXPIRY_DATE = 60 * 24;


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordForgotTokenRepository passwordForgotTokenRepository;

    public void createEmailConfirmToken(UserEntity user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(calculateExpiryDate());
        verificationTokenRepository.save(verificationToken);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, token));
    }

    public void createPasswordResetToken(String email) {
        UserEntity user = userService.findByEmail(email);
        if (Objects.isNull(user)) {
            return;
        }

        PasswordForgotToken passwordForgotToken = passwordForgotTokenRepository.findByUser(user)
                .orElse(null);
        if (Objects.isNull(passwordForgotToken)) {
            passwordForgotToken = new PasswordForgotToken();
            passwordForgotToken.setUser(user);
        }

        String token = UUID.randomUUID().toString();
        passwordForgotToken.setToken(token);
        passwordForgotToken.setExpiryDate(calculateExpiryDate());
        passwordForgotTokenRepository.save(passwordForgotToken);

        eventPublisher.publishEvent(new OnPasswordForgotRequestEvent(user, token));
    }

    public void validateEmail(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Null verification token"));


        UserEntity user = verificationToken.getUser();

        if (Objects.isNull(user)) {
            throw new ResourceNotFoundException("User not found");
        }

        checkTokenExpire(verificationToken.getExpiryDate());

        user.setEmailVerified(1);
        verificationTokenRepository.delete(verificationToken);
        userService.updateUser(user.getId(), user);
    }

    public void validateForgotPasswordConfirm(String token) {
        PasswordForgotToken passwordForgotToken = passwordForgotTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        checkTokenExpire(passwordForgotToken.getExpiryDate());
    }

    public void validateForgotPassword(PasswordForgotValidateDto passwordForgotValidateDto) {
        PasswordForgotToken passwordForgotToken = passwordForgotTokenRepository.findByToken(passwordForgotValidateDto.getToken())
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        UserEntity user = passwordForgotToken.getUser();

        if (Objects.isNull(user)) {
            throw new ResourceNotFoundException("User not found");
        }

        checkTokenExpire(passwordForgotToken.getExpiryDate());

        if (passwordEncoder.matches(passwordForgotValidateDto.getNewPassword(), user.getPassword())) {
            return;
        }

        user.setPassword(passwordEncoder.encode(passwordForgotValidateDto.getNewPassword()));
        userService.updateUser(user.getId(), user);
        passwordForgotTokenRepository.delete(passwordForgotToken);
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, TokenService.EXPIRY_DATE);
        return new Date(cal.getTime().getTime());
    }

    private void checkTokenExpire(Date date) {
        if ((date.getTime() - Calendar.getInstance().getTime().getTime()) <= 0) {
            throw new InvalidArgumentException("Token is expired");
        }

    }
}
