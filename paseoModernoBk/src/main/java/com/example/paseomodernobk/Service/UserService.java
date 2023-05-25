package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.UserEntity;
import com.example.paseomodernobk.Repository.CartItemRepository;
import com.example.paseomodernobk.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Page<UserEntity> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity findByEmail(String email) {
        UserEntity user = new UserEntity();
        try{
            Optional<UserEntity> userEntity = userRepository.findByEmail(email);
            if (userEntity.isPresent()){
                user = userEntity.get();
            }
            return user;
        }catch (Exception e){
            e.getStackTrace();
        }
        return user;
    }

    public UserEntity createUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserEntity updateUser(Long id, UserEntity user) {
        UserEntity existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setTelefono(user.getTelefono());
        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        cartItemRepository.deleteAll(cartItemRepository.findAllByUserId(id));
        userRepository.deleteById(id);
    }
}

