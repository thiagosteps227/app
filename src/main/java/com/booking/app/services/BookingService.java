package com.booking.app.services;

import com.booking.app.dtos.BookingDto;
import com.booking.app.dtos.UpdateBookingDto;
import com.booking.app.models.BlockModel;
import com.booking.app.models.BookingModel;
import com.booking.app.repositories.BlockRepository;
import com.booking.app.repositories.BookingRepository;
import com.booking.app.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Block;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

// BookingService.java
@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private BlockService blockService;

    public Object createBooking(BookingModel booking) {


        // Save the booking if it's valid
        return bookingRepository.save(booking);
    }

    public Optional<BookingModel> findById(UUID id) {
        return bookingRepository.findById(id);
    }

    public BookingModel saveBooking(BookingModel booking) {
            validateOverlappingBlock(booking);
            validateOverlappingOtherBooking(booking);
        return bookingRepository.save(booking);
    }

    private void validateOverlappingBlock(BookingModel booking) {
        UUID propertyId = booking.getProperty().getId();
        List<BlockModel> blocks = blockService.findByDateRange(booking.getStartDate(), booking.getEndDate(), propertyId);
        if (!blocks.isEmpty()) throw new IllegalArgumentException("Overlaps with existing block");
    }

    public void validateOverlappingOtherBooking(BookingModel booking) {
        List<BookingModel> bookings = bookingRepository.findActiveByDateRange(booking.getStartDate(), booking.getEndDate(), booking.getProperty().getId());
        Predicate<BookingModel> isSameBooking = b -> b.getId().equals(booking.getId());
        if (!bookings.isEmpty()
                && !bookings.stream().allMatch(isSameBooking)){
            throw new IllegalArgumentException("Booking overlaps with an existing booking by another guest");
        }
    }
    // delete booking by id
    public void deleteBooking(UUID id) {
        bookingRepository.deleteById(id);
    }
}
