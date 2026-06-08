package com.voltx.evgenee.dto.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MechanicDto {
    private String name;
    private String phone;
    private Double rating;
    private String speciality;
}