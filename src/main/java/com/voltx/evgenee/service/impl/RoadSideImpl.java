package com.voltx.evgenee.service.impl;

import com.voltx.evgenee.dto.requests.MessageRequestDto;
import com.voltx.evgenee.dto.requests.SosRequestDto;
import com.voltx.evgenee.dto.responses.MessageResponseDto;
import com.voltx.evgenee.dto.responses.SosResponseDto;
import com.voltx.evgenee.service.RoadsideService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoadSideImpl implements RoadsideService {


    @Override
    public List<String> getIssueTypes() {
        return List.of();
    }

    @Override
    public SosResponseDto.MechanicDto getNearestMechanic(Double latitude, Double longitude) {
        return null;
    }

    @Override
    public SosResponseDto createSOSRequest(SosRequestDto requestDto) {
        return null;
    }

    @Override
    public List<SosResponseDto> getMyRequests() {
        return List.of();
    }

    @Override
    public SosResponseDto getRequestDetails(Long requestId) {
        return null;
    }

    @Override
    public SosResponseDto cancelRequest(Long requestId) {
        return null;
    }
}
