package com.voltx.evgenee.service;

import com.voltx.evgenee.dto.requests.BookingRequestDto;
import com.voltx.evgenee.dto.responses.BookingResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingResponseDto validateBooking(BookingRequestDto requestDto);

    BookingResponseDto createBooking(BookingRequestDto requestDto);

    Object checkAvailability(Long stationId, LocalDate bookingDate);

    List<BookingResponseDto> getMyBookings();

    List<BookingResponseDto> getBookingsByStation(Long stationId);

    BookingResponseDto getBookingById(Long bookingId);

    BookingResponseDto cancelBooking(Long bookingId);

    BookingResponseDto checkInBooking(Long bookingId);

    BookingResponseDto completeBooking(Long bookingId);

    BookingResponseDto confirmAdvancePayment(Long bookingId);
}
