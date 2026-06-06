package com.voltx.evgenee.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stations")
public class StationsController {

    @GetMapping("/admin/all-stations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllStations() {
        return ResponseEntity.ok("");
    }
}
