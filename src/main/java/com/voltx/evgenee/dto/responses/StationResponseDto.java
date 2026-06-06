package com.voltx.evgenee.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationResponseDto {
    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer chargersCount;
    private Long ownerId;
    private String ownerName;
}
