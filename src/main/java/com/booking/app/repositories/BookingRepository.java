package com.booking.app.repositories;

import com.booking.app.dtos.BookingDto;
import com.booking.app.models.BookingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<BookingModel, UUID> {

    Object save(BookingDto booking);

    @Query("SELECT b FROM BookingModel b WHERE (b.startDate <= :endDate) AND (b.endDate >= :startDate) AND (b.property.id = :propertyId)")
    List<BookingModel> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("propertyId") UUID propertyId);

    //query to find by active booking
    @Query("SELECT b FROM BookingModel b WHERE (b.startDate <= :endDate) AND (b.endDate >= :startDate) AND (b.property.id = :propertyId) AND (b.status = 'active')")
    List<BookingModel> findActiveByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("propertyId") UUID propertyId);
 }
