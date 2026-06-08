package com.voltx.evgenee.dto.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeakPricingDto {
    private String startTime;
    private String endTime;
    private Double multiplier;
}
