package com.voltx.evgenee.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {
    private String name;
    private String email;
    private String password;
    private String role;
    private VehicleRequestDto vehicle;
    private List<String> vehicleNumbers;
    private List<VehicleRequestDto> savedVehicles;
}
