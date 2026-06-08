package com.voltx.evgenee.dto.responses;

import com.voltx.evgenee.dto.common.DataPayLoad;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiChatResponse {
    private boolean success;
    private DataPayLoad data;

}
