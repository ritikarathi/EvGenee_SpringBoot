package com.voltx.evgenee.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDto {
    private Long id;
    private Long userId;
    private String userName;
    private Long stationId;
    private String stationName;
    private Long vehicleId;
    private String vehicleModel;
    private Instant startTime;
    private Instant endTime;
    private String status;
    private PaymentResponseDto payment;
}
