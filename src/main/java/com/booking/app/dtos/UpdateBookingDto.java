package com.booking.app.dtos;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
public class UpdateBookingDto {

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private String guestName;

    private String guestEmail;

    private String status;
}
