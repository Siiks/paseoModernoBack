package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.UserAddressEntity;
import com.example.paseomodernobk.Exceptions.ResourceNotFoundException;
import com.example.paseomodernobk.Repository.UserAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressService {

    @Autowired
    private UserAddressRepository userAddressRepository;

    public UserAddressEntity createUserAddress(UserAddressEntity userAddress) {
        return userAddressRepository.save(userAddress);
    }

    public UserAddressEntity updateUserAddress(Long id, UserAddressEntity userAddress) {
        UserAddressEntity existingUserAddress = userAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User address not found with id " + id));
        existingUserAddress.setAddressLine1(userAddress.getAddressLine1());
        existingUserAddress.setAddressLine2(userAddress.getAddressLine2());
        existingUserAddress.setCity(userAddress.getCity());
        existingUserAddress.setPostalCode(userAddress.getPostalCode());
        existingUserAddress.setCountry(userAddress.getCountry());
        return userAddressRepository.save(existingUserAddress);
    }

    public List<UserAddressEntity> getAllUserAddresses(Long id) {
        return userAddressRepository.findAllByUserId(id);
    }

    public UserAddressEntity getUserAddressById(Long id) {
        return userAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User address not found with id " + id));
    }

    public void deleteUserAddress(Long id) {
        UserAddressEntity existingUserAddress = userAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User address not found with id " + id));
        userAddressRepository.delete(existingUserAddress);
    }
}

