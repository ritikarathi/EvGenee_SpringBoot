package com.voltx.evgenee.controller;

import com.voltx.evgenee.dto.requests.PaymentRequestDto;
import com.voltx.evgenee.dto.responses.PaymentResponseDto;
import com.voltx.evgenee.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<PaymentResponseDto> createOrder(
            @RequestBody PaymentRequestDto requestDto) {

        return ResponseEntity.ok(
                paymentService.createOrder(requestDto)
        );
    }

    @PostMapping("/update-payment")
    public ResponseEntity<PaymentResponseDto> updatePayment(
            @RequestBody PaymentRequestDto requestDto) {

        return ResponseEntity.ok(
                paymentService.updatePayment(requestDto)
        );
    }
}
