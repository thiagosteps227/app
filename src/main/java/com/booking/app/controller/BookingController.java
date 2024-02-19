package com.booking.app.controller;

import com.booking.app.dtos.BookingDto;
import com.booking.app.dtos.PatchBookingDto;
import com.booking.app.dtos.UpdateBookingDto;
import com.booking.app.models.BookingModel;
import com.booking.app.models.PropertyModel;
import com.booking.app.services.BookingService;
import com.booking.app.services.PropertyService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

// BookingController.java
@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public ResponseEntity<Object> createBooking(@RequestBody @Valid BookingDto bookingDto) {
        try {
            var bookingModel = new BookingModel();
            BeanUtils.copyProperties(bookingDto, bookingModel);
            Optional<PropertyModel> property = propertyService.getPropertyByName(bookingDto.getPropertyName());
            bookingModel.setProperty(property.get());
            bookingModel.setStatus("active");
            bookingService.saveBooking(bookingModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingModel);
        } catch (IllegalArgumentException e) {
            // Return 400 if there are overlapping bookings, with a custom error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBooking(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateBookingDto updateBookingDto) {
        try {
            Optional<BookingModel> booking = bookingService.findById(id);
            if (booking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
            }
            var bookingModel = booking.get();
            bookingModel.setStartDate(updateBookingDto.getStartDate());
            bookingModel.setEndDate(updateBookingDto.getEndDate());
            bookingModel.setGuestName(updateBookingDto.getGuestName());
            bookingModel.setGuestEmail(updateBookingDto.getGuestEmail());
            bookingModel.setStatus(updateBookingDto.getStatus());
            bookingService.saveBooking(bookingModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //delete booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable UUID id) {
        try {
            Optional<BookingModel> booking = bookingService.findById(id);
            if (booking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
            }
            bookingService.deleteBooking(id);
            return ResponseEntity.status(204).body("Booking deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBooking(@PathVariable UUID id) {
        try {
            Optional<BookingModel> booking = bookingService.findById(id);
            if (booking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(booking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> setBookingStatus(@PathVariable UUID id, @RequestBody PatchBookingDto patchBookingDto) {
        try {
            Optional<BookingModel> booking = bookingService.findById(id);
            if (booking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
            }
            var bookingModel = booking.get();
            bookingModel.setStatus(patchBookingDto.getStatus());
            bookingService.saveBooking(bookingModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
