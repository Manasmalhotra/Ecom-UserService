package com.example.userservice.controller;

import com.example.userservice.models.Address;
import com.example.userservice.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/{userId}/deliveryAddress")
public class AddressController {
    AddressService addressService;
    public AddressController(AddressService addressService){
        this.addressService=addressService;
    }
    @GetMapping
    public ResponseEntity<List<Address> > getAllAddress(@PathVariable("userId") int userId) {
           return ResponseEntity.ok(addressService.getAllAddresses(userId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable("userId") int userId, @PathVariable("id") int addressId){
        return ResponseEntity.ok(addressService.getAddress(userId,addressId));
    }
    @PostMapping
    public ResponseEntity<Address> createAddress(@PathVariable int userId,@RequestBody Address address){
        return new ResponseEntity<>(addressService.createAddress(userId,address), HttpStatus.CREATED);
    }
    @PutMapping ("/{addressId}")
    public ResponseEntity<Address> updateAddress(@PathVariable int userId,@PathVariable int addressId,@RequestBody Address address){
        return ResponseEntity.ok(addressService.updateAddress(userId,addressId,address));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable int userId,@PathVariable("id") int addressId){
        return ResponseEntity.ok(addressService.deleteAddress(userId, addressId));
    }

}
