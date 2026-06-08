package com.voltx.evgenee.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SosRequestDto {
    private Double latitude;
    private Double longitude;
    private String address;
    private String issueType;
    private String description;
    private Boolean requestTow;
}
