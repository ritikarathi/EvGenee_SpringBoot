package com.voltx.evgenee.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRequestDto {
    private String nickname;
    private String type;
    private String connectorType;
    private Double batteryCapacity;
    private String vehicleNumber;
}
