package com.voltx.evgenee.service.impl;

import com.voltx.evgenee.dto.requests.BookingRequestDto;
import com.voltx.evgenee.dto.responses.BookingResponseDto;
import com.voltx.evgenee.service.BookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Override
    public BookingResponseDto validateBooking(BookingRequestDto requestDto) {
        return null;
    }

    @Override
    public BookingResponseDto createBooking(BookingRequestDto requestDto) {
        return null;
    }

    @Override
    public Object checkAvailability(Long stationId, LocalDate bookingDate) {
        return null;
    }

    @Override
    public List<BookingResponseDto> getMyBookings() {
        return List.of();
    }

    @Override
    public List<BookingResponseDto> getBookingsByStation(Long stationId) {
        return List.of();
    }

    @Override
    public BookingResponseDto getBookingById(Long bookingId) {
        return null;
    }

    @Override
    public BookingResponseDto cancelBooking(Long bookingId, String reason) {
        return null;
    }

    @Override
    public BookingResponseDto checkInBooking(Long bookingId, String otp) {
        return null;
    }

    @Override
    public BookingResponseDto completeBooking(Long bookingId) {
        return null;
    }

    @Override
    public BookingResponseDto confirmAdvancePayment(Long bookingId) {
        return null;
    }
}
