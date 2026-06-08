package com.voltx.evgenee.service;

import com.voltx.evgenee.dto.requests.SosRequestDto;
import com.voltx.evgenee.dto.responses.SosResponseDto;

import java.util.List;

public interface RoadsideService {

    List<String> getIssueTypes();

    SosResponseDto.MechanicDto getNearestMechanic(
            Double latitude,
            Double longitude
    );

    SosResponseDto createSOSRequest(
            SosRequestDto requestDto
    );

    List<SosResponseDto> getMyRequests();

    SosResponseDto getRequestDetails(
            Long requestId
    );

    SosResponseDto cancelRequest(
            Long requestId
    );
}
