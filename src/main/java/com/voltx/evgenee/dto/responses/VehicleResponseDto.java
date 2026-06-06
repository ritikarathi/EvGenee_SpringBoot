package com.voltx.evgenee.dto.responses;


import com.voltx.evgenee.enums.VehicleType;
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
    private VehicleType type;
    private Double batteryCapacity;
    private Long ownerId;
}
