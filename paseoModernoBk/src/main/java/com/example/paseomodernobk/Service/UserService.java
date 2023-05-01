package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.UserEntity;
import com.example.paseomodernobk.Repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }


    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
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

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

