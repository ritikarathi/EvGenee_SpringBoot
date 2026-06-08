package com.voltx.evgenee.controller;

import com.voltx.evgenee.dto.common.MechanicDto;
import com.voltx.evgenee.dto.requests.MessageRequestDto;
import com.voltx.evgenee.dto.requests.SosRequestDto;
import com.voltx.evgenee.dto.responses.MessageResponseDto;
import com.voltx.evgenee.dto.responses.SosResponseDto;
import com.voltx.evgenee.service.RoadsideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roadside")
@RequiredArgsConstructor
public class RoadsideController {

    private final RoadsideService roadsideService;

    @GetMapping("/issue-types")
    public ResponseEntity<List<String>> getIssueTypes() {

        return ResponseEntity.ok(
                roadsideService.getIssueTypes()
        );
    }

    @GetMapping("/nearest-mechanic")
    public ResponseEntity<com.voltx.evgenee.dto.responses.SosResponseDto.MechanicDto> getNearestMechanic(
            @RequestParam Double latitude,
            @RequestParam Double longitude) {

        return ResponseEntity.ok(
                roadsideService.getNearestMechanic(latitude, longitude)
        );
    }

    @PostMapping("/sos")
    public ResponseEntity<SosResponseDto> createSOSRequest(
            @RequestBody SosRequestDto requestDto) {

        return ResponseEntity.ok(
                roadsideService.createSOSRequest(requestDto)
        );
    }


    @GetMapping("/my-requests")
    public ResponseEntity<List<SosResponseDto>> getMyRequests() {

        return ResponseEntity.ok(
                roadsideService.getMyRequests()
        );
    }

    @GetMapping("/sos/{requestId}")
    public ResponseEntity<SosResponseDto> getRequestDetails(
            @PathVariable Long requestId) {

        return ResponseEntity.ok(
                roadsideService.getRequestDetails(requestId)
        );
    }


    @PatchMapping("/sos/{requestId}/cancel")
    public ResponseEntity<SosResponseDto> cancelRequest(
            @PathVariable Long requestId) {

        return ResponseEntity.ok(
                roadsideService.cancelRequest(requestId)
        );
    }
}
