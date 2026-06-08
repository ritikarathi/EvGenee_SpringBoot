package com.voltx.evgenee.socket;

import lombok.Data;

@Data
public class VoiceChatRequest {
    private String message;
    private String threadId;
    private LocationData location;

    @Data
    public static class LocationData {
        private Double lat;
        private Double lng;
    }
}
