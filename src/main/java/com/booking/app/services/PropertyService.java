package com.booking.app.services;

import com.booking.app.models.PropertyModel;
import com.booking.app.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public PropertyModel createProperty(PropertyModel property) {
        return propertyRepository.save(property);
    }

    public Optional<PropertyModel> getPropertyByName(String propertyName) {
        return propertyRepository.findByPropertyName(propertyName);
    }

}
