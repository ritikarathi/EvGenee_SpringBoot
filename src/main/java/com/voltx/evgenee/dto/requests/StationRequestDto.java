package com.voltx.evgenee.dto.requests;

import jakarta.validation.constraints.Max;
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
public class StationRequestDto {
    @NotBlank(message = "Station name cannot be blank")
    private String name;

    @NotBlank(message = "Station address cannot be blank")
    private String address;

    @NotNull(message = "Latitude is required")
    @Min(value = -90, message = "Latitude must be >= -90")
    @Max(value = 90, message = "Latitude must be <= 90")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @Min(value = -180, message = "Longitude must be >= -180")
    @Max(value = 180, message = "Longitude must be <= 180")
    private Double longitude;

    @NotNull(message = "Chargers count is required")
    @Min(value = 1, message = "Station must have at least 1 charger")
    private Integer chargersCount;

    @NotNull(message = "Owner ID is required")
    private Long ownerId;
}
