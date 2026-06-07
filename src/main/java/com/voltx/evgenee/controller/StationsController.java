package com.voltx.evgenee.controller;


import com.voltx.evgenee.dto.requests.ReviewRequestDto;
import com.voltx.evgenee.dto.requests.StationRequestDto;
import com.voltx.evgenee.dto.responses.ReviewResponseDto;
import com.voltx.evgenee.dto.responses.StationResponseDto;
import com.voltx.evgenee.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
@RequiredArgsConstructor
public class StationsController {

    private final StationService stationService;

    // ==========================
    // PUBLIC APIs
    // ==========================

    @GetMapping("/nearby")
    public ResponseEntity<List<StationResponseDto>> getNearbyStations(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "10") Double radius) {

        return ResponseEntity.ok(
                stationService.getNearbyStations(
                        latitude,
                        longitude,
                        radius));
    }

    @GetMapping("/{stationId}")
    public ResponseEntity<StationResponseDto> getStationById(
            @PathVariable Long stationId) {

        return ResponseEntity.ok(
                stationService.getStationById(stationId));
    }

    @PostMapping("/{stationId}/review")
    public ResponseEntity<ReviewResponseDto> addReview(
            @PathVariable Long stationId,
            @RequestBody ReviewRequestDto request) {

        return ResponseEntity.ok(
                stationService.addReview(
                        stationId,
                        request));
    }

    // ==========================
    // OWNER / ADMIN APIs
    // ==========================

    @PostMapping("/add")
    public ResponseEntity<StationResponseDto> addStation(
            @RequestBody StationRequestDto request) {

        return ResponseEntity.ok(
                stationService.addStation(request));
    }

    @GetMapping("/owner/my-stations")
    public ResponseEntity<List<StationResponseDto>> getMyStations(
            Authentication authentication) {

        return ResponseEntity.ok(
                stationService.getMyStations(
                        authentication.getName()));
    }

    @PutMapping("/{stationId}")
    public ResponseEntity<StationResponseDto> updateStation(
            @PathVariable Long stationId,
            @RequestBody StationRequestDto request) {

        return ResponseEntity.ok(
                stationService.updateStation(
                        stationId,
                        request));
    }

    @PatchMapping("/{stationId}/toggle")
    public ResponseEntity<String> toggleStationStatus(
            @PathVariable Long stationId) {

        stationService.toggleStationStatus(stationId);

        return ResponseEntity.ok(
                "Station status updated successfully");
    }

    // ==========================
    // ADMIN APIs
    // ==========================

    @GetMapping("/admin/all-stations")
    public ResponseEntity<List<StationResponseDto>> getAllStations() {

        return ResponseEntity.ok(
                stationService.getAllStations());
    }

    @GetMapping("/admin/owner/{ownerId}")
    public ResponseEntity<List<StationResponseDto>> getStationsByOwner(
            @PathVariable Long ownerId) {

        return ResponseEntity.ok(
                stationService.getStationsByOwner(ownerId));
    }

    @PutMapping("/admin/{stationId}/status")
    public ResponseEntity<String> updateStationStatus(
            @PathVariable Long stationId,
            @RequestParam String status) {

        stationService.updateStationStatus(
                stationId,
                status);

        return ResponseEntity.ok(
                "Station status updated successfully");
    }

    @PutMapping("/admin/{stationId}/suspend")
    public ResponseEntity<String> suspendStation(
            @PathVariable Long stationId) {

        stationService.suspendStation(stationId);

        return ResponseEntity.ok(
                "Station suspended successfully");
    }

    @DeleteMapping("/admin/{stationId}")
    public ResponseEntity<String> deleteStation(
            @PathVariable Long stationId) {

        stationService.deleteStation(stationId);

        return ResponseEntity.ok(
                "Station deleted successfully");
    }
}