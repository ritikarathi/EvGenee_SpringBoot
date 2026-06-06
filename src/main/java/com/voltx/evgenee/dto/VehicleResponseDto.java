package com.voltx.evgenee.dto;

import com.voltx.evgenee.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponseDto {
    private Long id;
    private String model;
    private String licensePlate;
    private Type type;
    private Double batteryCapacity;
    private Long ownerId;
}
