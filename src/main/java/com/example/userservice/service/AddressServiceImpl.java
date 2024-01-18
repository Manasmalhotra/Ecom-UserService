package com.example.userservice.service;

import com.example.userservice.exception.AddressNotMatchingException;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.models.Address;
import com.example.userservice.models.UserEntity;
import com.example.userservice.repository.AddressRepository;
import com.example.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AddressServiceImpl implements AddressService {
    AddressRepository addressRepository;
    UserRepository userRepository;
    public AddressServiceImpl(UserRepository userRepository,AddressRepository addressRepository){
        this.userRepository=userRepository;
        this.addressRepository=addressRepository;
    }

    private UserEntity getUser(int userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","ID",Integer.toString(userId)));
    }

    @Override
    public Address getAddress(int userId, int addressId) {
        UserEntity user=getUser(userId);
        Address address=addressRepository.findById(addressId).orElseThrow(()->new ResourceNotFoundException("Address","ID",Integer.toString(addressId)));
        if(user.getId()!=address.getUser().getId()) {
            throw new AddressNotMatchingException(HttpStatus.BAD_REQUEST,"Address does not belong to user");
        }
        return  address;
    }

    @Override
    public List<Address> getAllAddresses(int userId) {
        UserEntity user=getUser(userId);
        return user.getAddress();
    }

    @Override
    public Address createAddress(int userId, Address address) {
        UserEntity user=getUser(userId);
        address.setUser(user);
        addressRepository.save(address);
        return address;
    }

    @Override
    public Address updateAddress(int userId, int addressId,Address address) {
        Address address1=getAddress(userId, addressId);
        address1.setArea(address.getArea());
        address1.setLandmark(address.getLandmark());
        address1.setHouseNumber(address.getHouseNumber());
        address1.setPinCode(address.getPinCode());
        address1.setStreetName(address.getStreetName());
        address1.setUser(address.getUser());
        return addressRepository.save(address1);
    }

    @Override
    public String deleteAddress(int userId, int addressId) {
        Address address=getAddress(userId, addressId);
        addressRepository.delete(address);
        return "Address Deleted Successfully";
    }
}
