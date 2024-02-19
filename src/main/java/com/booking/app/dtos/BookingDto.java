package com.booking.app.dtos;

import com.booking.app.models.PropertyModel;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
public class BookingDto {

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private String guestName;

    @NotNull
    private String guestEmail;

    @NotNull
    private String propertyName;
}
