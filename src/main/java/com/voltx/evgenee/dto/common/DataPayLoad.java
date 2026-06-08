package com.voltx.evgenee.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataPayLoad {
    private String response;
    private String threadId;
    private String bookingId;
    private Boolean redirect;
    private Object stations;
}
