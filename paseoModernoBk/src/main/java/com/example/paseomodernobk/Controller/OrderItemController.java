package com.example.paseomodernobk.Controller;

import com.example.paseomodernobk.Entity.OrderItemEntity;
import com.example.paseomodernobk.Service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderItems")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public List<OrderItemEntity> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderItemEntity>> getOrderItemByIdUser(@PathVariable Long id) {
        List<OrderItemEntity> orderItem = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(orderItem);
    }

    @PostMapping
    public ResponseEntity<List<OrderItemEntity>> createOrderItem(@RequestBody List<OrderItemEntity> orderItems) {
        List<OrderItemEntity> newOrderItems = orderItemService.createOrderItem(orderItems);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrderItems);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }

}
