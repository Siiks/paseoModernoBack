package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.OrderItemEntity;
import com.example.paseomodernobk.Exceptions.ResourceNotFoundException;
import com.example.paseomodernobk.Repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItemEntity> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItemEntity getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", id));
    }

    public OrderItemEntity createOrderItem(OrderItemEntity orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public OrderItemEntity updateOrderItem(Long id, OrderItemEntity orderItemDetails) {
        OrderItemEntity orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", id));

        orderItem.setOrder(orderItemDetails.getOrder());
        orderItem.setProduct(orderItemDetails.getProduct());
        orderItem.setQuantity(orderItemDetails.getQuantity());
        orderItem.setPrice(orderItemDetails.getPrice());

        return orderItemRepository.save(orderItem);
    }

    public void deleteOrderItem(Long id) {
        OrderItemEntity orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", id));

        orderItemRepository.delete(orderItem);
    }
}

