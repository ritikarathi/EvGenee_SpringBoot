package com.voltx.evgenee.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDto {
    private String station;
    private String connectorType;
    private String date;
    private String startTime;
    private String endTime;
    private String vehicleNumber;
}
