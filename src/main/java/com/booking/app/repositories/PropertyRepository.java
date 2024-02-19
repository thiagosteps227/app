package com.booking.app.repositories;

import com.booking.app.models.BookingModel;
import com.booking.app.models.PropertyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<PropertyModel, UUID> {

    PropertyModel save(PropertyModel property);

    // find property by name
    Optional<PropertyModel> findByPropertyName(String propertyName);

}
