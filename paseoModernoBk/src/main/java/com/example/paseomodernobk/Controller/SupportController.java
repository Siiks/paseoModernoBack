package com.example.paseomodernobk.Controller;

import com.example.paseomodernobk.Entity.SupportEntity;
import com.example.paseomodernobk.Service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/support")
public class SupportController {

    @Autowired
    SupportService supportService;

    @PostMapping
    public ResponseEntity<Void> sendSupportMessage(@RequestBody SupportEntity supportEntity) {
        supportService.sendConfirmationEmail(supportEntity);
        return ResponseEntity.ok().build();
    }
}
