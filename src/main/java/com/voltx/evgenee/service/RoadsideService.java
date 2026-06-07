package com.voltx.evgenee.service;

import com.voltx.evgenee.dto.requests.MessageRequestDto;
import com.voltx.evgenee.dto.responses.MessageResponseDto;

import java.util.List;

public interface RoadsideService {

    List<String> getIssueTypes();

    MessageResponseDto getNearestMechanic(
            Double latitude,
            Double longitude
    );

    MessageResponseDto createSOSRequest(
            MessageRequestDto requestDto
    );

    List<MessageResponseDto> getMyRequests();

    MessageResponseDto getRequestDetails(
            Long requestId
    );

    MessageResponseDto cancelRequest(
            Long requestId
    );
}
