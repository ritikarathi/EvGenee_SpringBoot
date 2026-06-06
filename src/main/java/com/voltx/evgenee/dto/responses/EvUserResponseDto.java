package com.voltx.evgenee.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvUserResponseDto {
    private Long id;
    private String fullName;
    private String phone;
    private String address;
    private UserResponseDto authUser;
    private List<VehicleResponseDto> vehicles;
}
