package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.Dto.MessageResponse;
import com.example.paseomodernobk.Entity.CartItemEntity;
import com.example.paseomodernobk.Repository.CartItemRepository;
import com.example.paseomodernobk.Repository.ProductRepository;
import com.example.paseomodernobk.Repository.UserRepository;
import com.example.paseomodernobk.Utils.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CartItemEntity> getAllCartItems() {
        return cartItemRepository.findAll().stream()
                .peek(cartItemEntity -> {
                    cartItemEntity.getProduct().getFotos().forEach(fotoEntity -> {
                        fotoEntity.setImage(ImageUtility.decompressImage(fotoEntity.getImage()));
                    });
                })
                .collect(Collectors.toList());
    }

    public List<CartItemEntity> getCartItemById(Long id) {
        return cartItemRepository.findAllByUserId(id);
    }

    public CartItemEntity saveCartItem(CartItemEntity cartItem) {
        List<CartItemEntity> items = getCartItemById(cartItem.getUser().getId());

        for (CartItemEntity item : items) {
            if (Objects.equals(item.getProduct().getId(), cartItem.getProduct().getId())) {
                int newQuantity = item.getQuantity() + cartItem.getQuantity();
                item.setQuantity(newQuantity);
                return cartItemRepository.save(item);
            }
        }
        return cartItemRepository.save(cartItem);
    }


    public List<CartItemEntity> saveModifiedItems(List<CartItemEntity> cartItemEntities){
        return cartItemRepository.saveAll(cartItemEntities);
    }

    public MessageResponse deleteCartItemById(Long id) {
        cartItemRepository.deleteById(id);
        return new MessageResponse("El producto se ha borrado con éxito");
    }

    public void deleteAllCartItems() {
        cartItemRepository.deleteAll();
    }


    public void deleteAllCartItemsById(Long id) {
        List<CartItemEntity> items = getCartItemById(id);
        items.forEach(item  ->{
            cartItemRepository.delete(item);
        });
    }
}

