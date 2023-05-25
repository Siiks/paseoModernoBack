package com.example.paseomodernobk.Controller;

import com.example.paseomodernobk.Entity.UserAddressEntity;
import com.example.paseomodernobk.Service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-addresses")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @PostMapping
    public ResponseEntity<UserAddressEntity> createUserAddress(@RequestBody UserAddressEntity userAddress) {
        UserAddressEntity createdUserAddress = userAddressService.createUserAddress(userAddress);
        return new ResponseEntity<>(createdUserAddress, HttpStatus.CREATED);
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<List<UserAddressEntity>> getUserAddressById(@PathVariable Long id) {
        List<UserAddressEntity> userAddress = userAddressService.getAllUserAddresses(id);
        return new ResponseEntity<>(userAddress, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserAddressEntity> getAllUserAddresses(@PathVariable Long id) {
        UserAddressEntity userAddresses = userAddressService.getUserAddressById(id);
        return new ResponseEntity<>(userAddresses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAddressEntity> updateUserAddress(@PathVariable Long id, @RequestBody UserAddressEntity userAddress) {
        UserAddressEntity updatedUserAddress = userAddressService.updateUserAddress(id, userAddress);
        return new ResponseEntity<>(updatedUserAddress, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAddress(@PathVariable Long id) {
        userAddressService.deleteUserAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
