package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.OrderItemEntity;
import com.example.paseomodernobk.Exceptions.ResourceNotFoundException;
import com.example.paseomodernobk.Repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItemEntity> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public List<OrderItemEntity> getOrderItemById(Long id) {
        return orderItemRepository.findByIdUser(id);
    }

    public List<OrderItemEntity> createOrderItem(List<OrderItemEntity> orderItems) {
        List<OrderItemEntity> savedOrderItems = new ArrayList<>();
        for (OrderItemEntity orderItem : orderItems) {
            OrderItemEntity savedOrderItem = orderItemRepository.save(orderItem);
            savedOrderItems.add(savedOrderItem);
        }
        return savedOrderItems;
    }


    public OrderItemEntity createOrders(OrderItemEntity orderItems) {
        return orderItemRepository.save(orderItems);
    }


    public void deleteOrderItem(Long id) {
        OrderItemEntity orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", id));

        orderItemRepository.delete(orderItem);
    }
}

