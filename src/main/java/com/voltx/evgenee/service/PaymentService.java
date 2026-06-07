package com.voltx.evgenee.service;
import com.voltx.evgenee.dto.requests.PaymentRequestDto;
import com.voltx.evgenee.dto.responses.PaymentResponseDto;

public interface PaymentService {

    PaymentResponseDto createOrder(PaymentRequestDto requestDto);

    PaymentResponseDto updatePayment(PaymentRequestDto requestDto);
}