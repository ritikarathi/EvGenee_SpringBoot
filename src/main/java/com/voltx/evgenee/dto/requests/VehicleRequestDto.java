package com.voltx.evgenee.dto.requests;

import com.voltx.evgenee.enums.VehicleType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRequestDto {
    @NotBlank(message = "Vehicle model cannot be blank")
    private String model;

    @NotBlank(message = "License plate cannot be blank")
    private String licensePlate;

    @NotNull(message = "Vehicle type is required")
    private VehicleType type;

    @NotNull(message = "Battery capacity is required")
    @Min(value = 0, message = "Battery capacity must be greater than or equal to 0")
    private Double batteryCapacity;

    @NotNull(message = "Owner ID is required")
    private Long ownerId;
}
