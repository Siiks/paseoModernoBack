package com.example.paseomodernobk.Controller;

import com.example.paseomodernobk.Entity.CartItemEntity;
import com.example.paseomodernobk.Entity.Dto.CartItemDTO;
import com.example.paseomodernobk.Service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cartItems")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<List<CartItemEntity>> getAllCartItems() {
        List<CartItemEntity> cartItems = cartItemService.getAllCartItems();
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemEntity> getCartItemById(@PathVariable("id") Long id) {
        Optional<CartItemEntity> cartItem = cartItemService.getCartItemById(id);
        return cartItem.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CartItemEntity> createCartItem(@RequestBody CartItemDTO cartItem) {
        CartItemEntity savedCartItem = cartItemService.saveCartItem(cartItem);
        return new ResponseEntity<>(savedCartItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemEntity> updateCartItem(@RequestBody CartItemDTO cartItem) {
            return new ResponseEntity<>(cartItemService.saveCartItem(cartItem), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCartItemById(@PathVariable("id") Long id) {
        Optional<CartItemEntity> cartItem = cartItemService.getCartItemById(id);
        if (cartItem.isPresent()) {
            cartItemService.deleteCartItemById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllCartItems() {
        cartItemService.deleteAllCartItems();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

