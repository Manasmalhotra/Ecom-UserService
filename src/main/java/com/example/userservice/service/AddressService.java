package com.example.userservice.service;

import com.example.userservice.models.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAllAddresses(int userId);
    Address getAddress(int userId,int addressId);
    Address createAddress(int userId,Address address);
    Address updateAddress(int userId,int addressId,Address address);
    String deleteAddress(int userId,int addressId);

}
