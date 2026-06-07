package com.voltx.evgenee.controller;


import com.voltx.evgenee.dto.requests.BookingRequestDto;
import com.voltx.evgenee.dto.responses.BookingResponseDto;
import com.voltx.evgenee.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/validate")
    public ResponseEntity<BookingResponseDto> validateBooking(
            @RequestBody BookingRequestDto requestDto) {

        return ResponseEntity.ok(
                bookingService.validateBooking(requestDto)
        );
    }

    @PostMapping("/create")
    public ResponseEntity<BookingResponseDto> createBooking(
            @RequestBody BookingRequestDto requestDto) {

        return ResponseEntity.ok(
                bookingService.createBooking(requestDto)
        );
    }


    @GetMapping("/availability")
    public ResponseEntity<?> checkAvailability(
            @RequestParam Long stationId,
            @RequestParam LocalDate bookingDate) {

        return ResponseEntity.ok(
                bookingService.checkAvailability(stationId, bookingDate)
        );
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingResponseDto>> getMyBookings() {

        return ResponseEntity.ok(
                bookingService.getMyBookings()
        );
    }

    @GetMapping("/station/{stationId}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByStation(
            @PathVariable Long stationId) {

        return ResponseEntity.ok(
                bookingService.getBookingsByStation(stationId)
        );
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDto> getBookingById(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                bookingService.getBookingById(bookingId)
        );
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponseDto> cancelBooking(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                bookingService.cancelBooking(bookingId)
        );
    }

    @PostMapping("/{bookingId}/check-in")
    public ResponseEntity<BookingResponseDto> checkInBooking(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                bookingService.checkInBooking(bookingId)
        );
    }

    @PostMapping("/{bookingId}/complete")
    public ResponseEntity<BookingResponseDto> completeBooking(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                bookingService.completeBooking(bookingId)
        );
    }

    @PostMapping("/{bookingId}/confirm-advance")
    public ResponseEntity<BookingResponseDto> confirmAdvancePayment(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                bookingService.confirmAdvancePayment(bookingId)
        );
    }
}