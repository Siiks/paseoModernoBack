package com.example.paseomodernobk.Controller;

import com.example.paseomodernobk.Entity.UserPaymentEntity;
import com.example.paseomodernobk.Exceptions.ResourceNotFoundException;
import com.example.paseomodernobk.Service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-payments")
public class UserPaymentController {

    @Autowired
    private UserPaymentService userPaymentService;

    // Create
    @PostMapping("/")
    public ResponseEntity<UserPaymentEntity> createUserPayment(@RequestBody UserPaymentEntity userPayment) {
        UserPaymentEntity newUserPayment = userPaymentService.saveUserPayment(userPayment);
        return new ResponseEntity<>(newUserPayment, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/{id}")
    public ResponseEntity<UserPaymentEntity> getUserPaymentById(@PathVariable(value = "id") Long id) {
        UserPaymentEntity userPayment = userPaymentService.getUserPaymentById(id);
        return new ResponseEntity<>(userPayment, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserPaymentEntity>> getAllUserPayments() {
        List<UserPaymentEntity> userPayments = userPaymentService.getAllUserPayments();
        return new ResponseEntity<>(userPayments, HttpStatus.OK);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<UserPaymentEntity> updateUserPayment(@PathVariable(value = "id") Long id, @RequestBody UserPaymentEntity userPaymentDetails) {
        UserPaymentEntity updatedUserPayment = userPaymentService.updateUserPayment(id, userPaymentDetails);
        return new ResponseEntity<>(updatedUserPayment, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPayment(@PathVariable(value = "id") Long id) {
        userPaymentService.deleteUserPayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
