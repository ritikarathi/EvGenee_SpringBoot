package com.voltx.evgenee.controller;

import com.voltx.evgenee.dto.requests.MessageRequestDto;
import com.voltx.evgenee.dto.responses.MessageResponseDto;
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

    /**
     * Get supported SOS issue types
     */
    @GetMapping("/issue-types")
    public ResponseEntity<List<String>> getIssueTypes() {

        return ResponseEntity.ok(
                roadsideService.getIssueTypes()
        );
    }

    /**
     * Find nearest mechanic
     */
    @GetMapping("/nearest-mechanic")
    public ResponseEntity<MessageResponseDto> getNearestMechanic(
            @RequestParam Double latitude,
            @RequestParam Double longitude) {

        return ResponseEntity.ok(
                roadsideService.getNearestMechanic(latitude, longitude)
        );
    }

    /**
     * Create SOS Request
     */
    @PostMapping("/sos")
    public ResponseEntity<MessageResponseDto> createSOSRequest(
            @RequestBody MessageRequestDto requestDto) {

        return ResponseEntity.ok(
                roadsideService.createSOSRequest(requestDto)
        );
    }

    /**
     * Get all SOS requests of logged-in user
     */
    @GetMapping("/my-requests")
    public ResponseEntity<List<MessageResponseDto>> getMyRequests() {

        return ResponseEntity.ok(
                roadsideService.getMyRequests()
        );
    }

    /**
     * Get SOS request details
     */
    @GetMapping("/sos/{requestId}")
    public ResponseEntity<MessageResponseDto> getRequestDetails(
            @PathVariable Long requestId) {

        return ResponseEntity.ok(
                roadsideService.getRequestDetails(requestId)
        );
    }

    /**
     * Cancel SOS request
     */
    @PatchMapping("/sos/{requestId}/cancel")
    public ResponseEntity<MessageResponseDto> cancelRequest(
            @PathVariable Long requestId) {

        return ResponseEntity.ok(
                roadsideService.cancelRequest(requestId)
        );
    }
}
