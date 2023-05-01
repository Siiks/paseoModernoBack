package com.example.paseomodernobk.Events;

import com.example.paseomodernobk.Entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnPasswordForgotRequestEvent extends ApplicationEvent {

    private UserEntity user;
    private String token;

    public OnPasswordForgotRequestEvent(UserEntity user, String token) {
        super(user);
        this.user = user;
        this.token = token;
    }
}