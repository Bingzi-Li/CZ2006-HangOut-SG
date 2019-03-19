package com.skyforce.SkyForceWebService.controller;


import com.skyforce.SkyForceWebService.config.JSONConvert;
import com.skyforce.SkyForceWebService.model.Vendor;
import com.skyforce.SkyForceWebService.service.ShopService;
import com.skyforce.SkyForceWebService.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;
import java.util.List;

@RestController
public class VendorController {

    // TODO:  Change http status for different exceptions

    // TODO: add service for vendor to add shop

    @Autowired
    VendorService vendorService;

    @Autowired
    ShopService shopService;

    private AtomicLong nextId = new AtomicLong();

    // TODO: Need a machanism to remember the last assigned id

    private long getRecentId () {
        List<Vendor> vendors = vendorService.findAll();
        Long recentId = 0L;
        for (Vendor vendor : vendors) {
            if (vendor.getId() > recentId) {
                recentId = vendor.getId();
            }
        }
        return recentId;
    }

    // Get all the vendors
    @GetMapping("/vendors")
    public String getAllVendors() {
        List<Vendor> vendors = vendorService.findAll();
        return JSONConvert.JSONConverter(vendors);
    }

    // Get vendor by ID
    @GetMapping("/vendor")
    public String getVendorById(
            @RequestParam Long vendorId
    ) {
        Vendor vendor = vendorService.findVendorById(vendorId);
        return JSONConvert.JSONConverter(vendor);
    }

    // Vendor Sign in
    @GetMapping("/vendor/signin/{email}/{password}")
    public String signInVendor (@PathVariable("email") String email, @PathVariable("password") String password) throws NoSuchAlgorithmException {
        Vendor vendor = vendorService.findVendorByEmail(email);
        return ValidationController.UserSignIn(vendor, password);
    }
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED,
            reason = "Email or password incorrect")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badAuthenticationException() {

    }

    // Vendor registration
    @PostMapping("/vendor")
    @ResponseStatus(HttpStatus.CREATED)
    public String createVendor(
            @Valid @RequestBody Vendor vendor
    ) throws NoSuchAlgorithmException {
//        vendor.setId(nextId.incrementAndGet());
        String hashedPassword = vendor.hashPassword(vendor.getPassword());
        vendor.setPassword(hashedPassword);
        return JSONConvert.JSONConverter(vendorService.save(vendor));
    }

    // Change Vendor account information
    @PutMapping("/vendor")
    public String updateVendorById(
            @RequestParam Long vendorId,
            @Valid @RequestBody Vendor vendor) {
        Vendor oldVendor = vendorService.findVendorById(vendorId);
        oldVendor.setFirstName(vendor.getFirstName());
        oldVendor.setLastName(vendor.getLastName());
        oldVendor.setGender(vendor.getGender());
//        The setShops should not be here, it should be done with the add and delete shop.
//        oldVendor.setShops(vendor.getShops());
        Vendor updatedVendor = vendorService.save(oldVendor);
        return JSONConvert.JSONConverter(updatedVendor) ;
    }

    // Delete vendor account
    @DeleteMapping("/vendor")
    public ResponseEntity<?> deleteVendor(
            @RequestParam Long vendorId
    ) {
        Vendor vendor = vendorService.findVendorById(vendorId);
        vendorService.delete(vendor);
        return ResponseEntity.ok().build();
    }
}
