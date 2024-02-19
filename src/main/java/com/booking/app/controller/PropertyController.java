package com.booking.app.controller;

import com.booking.app.models.PropertyModel;
import com.booking.app.services.PropertyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/properties")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public ResponseEntity<Object> createBooking(@RequestBody @Valid PropertyModel property) {
        try {
                PropertyModel createdProperty = propertyService.createProperty(property);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProperty);
        } catch (IllegalArgumentException e) {
            // Return 400 if there are overlapping bookings, with a custom error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
