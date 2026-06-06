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
public class StationOwnerResponseDto {
    private Long id;
    private String name;
    private String contact;
    private UserResponseDto authUser;
    private List<StationResponseDto> stations;
}
