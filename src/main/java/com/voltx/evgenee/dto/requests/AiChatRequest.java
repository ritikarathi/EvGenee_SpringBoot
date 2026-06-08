package com.voltx.evgenee.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiChatRequest {
    private String message;
    private String threadId;
    private LocationData location;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationData {
        private Double lat;
        private Double lng;
    }
}
