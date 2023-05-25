package com.example.paseomodernobk.Controller;

import com.example.paseomodernobk.Entity.Dto.PaymentIntentDto;
import com.example.paseomodernobk.Entity.OrderEntity;
import com.example.paseomodernobk.Entity.OrderItemEntity;
import com.example.paseomodernobk.Repository.OrderRepository;
import com.example.paseomodernobk.Service.OrderItemService;
import com.example.paseomodernobk.Service.OrderService;
import com.example.paseomodernobk.Service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    OrderItemService orderService;

    @PostMapping("/pay")
    public ResponseEntity<String> payment (@RequestBody PaymentIntentDto paymentIntentDto) throws StripeException {
        PaymentIntent paymentIntent = paymentService.paymentIntent(paymentIntentDto);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<String> confirm (@PathVariable("id") String id, @RequestBody List<OrderItemEntity> order) throws StripeException {
        PaymentIntent paymentIntent = paymentService.confirm(id);
        orderService.createOrderItem(order);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancel (@PathVariable("id") String id) throws StripeException {
        PaymentIntent paymentIntent = paymentService.cancel(id);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }
}
