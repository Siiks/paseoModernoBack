package com.example.paseomodernobk.Controller;

import com.example.paseomodernobk.Entity.Dto.MessageResponse;
import com.example.paseomodernobk.Entity.CartItemEntity;
import com.example.paseomodernobk.Service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<List<CartItemEntity>> getCartItemByIdUser(@PathVariable("id") Long id) {
        List<CartItemEntity> cartItem = cartItemService.getCartItemById(id);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartItemEntity> createCartItem(@RequestBody CartItemEntity cartItem) {
        CartItemEntity savedCartItem = cartItemService.saveCartItem(cartItem);
        return new ResponseEntity<>(savedCartItem, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<List<CartItemEntity>> updateCartItem(@RequestBody List<CartItemEntity> cartItem) {
            return new ResponseEntity<>(cartItemService.saveModifiedItems(cartItem), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCartItemById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cartItemService.deleteCartItemById(id), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteAllCartItemsById(@PathVariable("id") Long id) {
        cartItemService.deleteAllCartItemsById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

