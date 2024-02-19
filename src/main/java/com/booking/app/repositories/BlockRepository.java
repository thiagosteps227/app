package com.booking.app.repositories;

import com.booking.app.models.BlockModel;
import org.springframework.cglib.core.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BlockRepository extends JpaRepository<BlockModel, UUID> {
    @Query("SELECT b FROM BlockModel b WHERE (b.startDate <= :endDate) AND (b.endDate >= :startDate) AND (b.property.id = :propertyId)")
    List<BlockModel> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("propertyId") UUID propertyId);

}
