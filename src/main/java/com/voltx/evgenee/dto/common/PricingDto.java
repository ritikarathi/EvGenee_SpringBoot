package com.voltx.evgenee.dto.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingDto {
    private Double priceperKWh;
    private String connectorType;
    private Integer portCount;
    private String currency;
}