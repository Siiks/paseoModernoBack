package com.example.paseomodernobk.Controller;

import com.example.paseomodernobk.Entity.OrderItemEntity;
import com.example.paseomodernobk.Service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderItems")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public List<OrderItemEntity> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemEntity> getOrderItemById(@PathVariable Long id) {
        OrderItemEntity orderItem = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(orderItem);
    }

    @PostMapping
    public ResponseEntity<OrderItemEntity> createOrderItem(@RequestBody OrderItemEntity orderItem) {
        OrderItemEntity newOrderItem = orderItemService.createOrderItem(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrderItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemEntity> updateOrderItem(@PathVariable Long id, @RequestBody OrderItemEntity orderItem) {
        OrderItemEntity updatedOrderItem = orderItemService.updateOrderItem(id, orderItem);
        return ResponseEntity.ok(updatedOrderItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }

}
