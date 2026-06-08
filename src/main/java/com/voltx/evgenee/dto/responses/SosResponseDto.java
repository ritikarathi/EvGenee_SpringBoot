package com.voltx.evgenee.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SosResponseDto {
    private String requestId;
    private String status;
    private String issueType;
    private String issueLabel;
    private Boolean towRequested;
    private String address;
    private String description;
    private MechanicDto mechanic;
    private String createdAt;
    private String resolvedAt;
    private String cancelledAt;
    private String value;
    private String label;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MechanicDto {
        private String name;
        private String phone;
        private String garage;
        private String estimatedArrival;
        private String distance;
        private Double rating;
        private String speciality;
    }
}
