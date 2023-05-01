package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.CartItemEntity;
import com.example.paseomodernobk.Entity.Dto.CartItemDTO;
import com.example.paseomodernobk.Entity.Dto.OrderItemDTO;
import com.example.paseomodernobk.Entity.OrderEntity;
import com.example.paseomodernobk.Entity.OrderItemEntity;
import com.example.paseomodernobk.Entity.UserEntity;
import com.example.paseomodernobk.Repository.CartItemRepository;
import com.example.paseomodernobk.Repository.ProductRepository;
import com.example.paseomodernobk.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CartItemEntity> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public Optional<CartItemEntity> getCartItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    public CartItemEntity saveCartItem(CartItemDTO cartItem) {
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setProduct(productRepository.findById(cartItem.getProductId()).get());
        cartItemEntity.setUser(userRepository.findById(cartItem.getUserId()).get());
        cartItemEntity.setQuantity(cartItem.getQuantity() > 1 ? cartItem.getQuantity() : 1);
        return cartItemRepository.save(cartItemEntity);
    }

    public void deleteCartItemById(Long id) {
        cartItemRepository.deleteById(id);
    }

    public void deleteAllCartItems() {
        cartItemRepository.deleteAll();
    }

}

