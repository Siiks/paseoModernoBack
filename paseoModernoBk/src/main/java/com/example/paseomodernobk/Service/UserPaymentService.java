package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.UserPaymentEntity;
import com.example.paseomodernobk.Exceptions.ResourceNotFoundException;
import com.example.paseomodernobk.Repository.UserPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPaymentService {

    @Autowired
    private UserPaymentRepository userPaymentRepository;

    // Create
    public UserPaymentEntity saveUserPayment(UserPaymentEntity userPayment) {
        return userPaymentRepository.save(userPayment);
    }

    // Read
    public UserPaymentEntity getUserPaymentById(Long id) {
        return userPaymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Payment", "id", id));
    }

    public List<UserPaymentEntity> getAllUserPayments() {
        return userPaymentRepository.findAll();
    }

    // Update
    public UserPaymentEntity updateUserPayment(Long id, UserPaymentEntity userPaymentDetails) {
        UserPaymentEntity userPayment = getUserPaymentById(id);
        userPayment.setPaymentType(userPaymentDetails.getPaymentType());
        userPayment.setCardNumber(userPaymentDetails.getCardNumber());
        userPayment.setCardExpiry(userPaymentDetails.getCardExpiry());
        userPayment.setBankNumber(userPaymentDetails.getBankNumber());
        return userPaymentRepository.save(userPayment);
    }

    // Delete
    public void deleteUserPayment(Long id) {
        UserPaymentEntity userPayment = getUserPaymentById(id);
        userPaymentRepository.delete(userPayment);
    }
}

