package com.example.paseomodernobk.Entity.Dto;

import com.example.paseomodernobk.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressDTO {

    private int id;
    private UserEntity user;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postalCode;
    private String country;

}
