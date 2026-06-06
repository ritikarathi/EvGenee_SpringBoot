package com.voltx.evgenee.dto.requests;

import com.voltx.evgenee.enums.PaymentMethod;
import com.voltx.evgenee.enums.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {
    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Amount must be greater than or equal to 0")
    private BigDecimal amount;

    @NotBlank(message = "Payment method cannot be blank")
    private PaymentMethod method;

    @NotBlank(message = "Payment status cannot be blank")
    private PaymentStatus status;

    private String transactionId;
    private Instant paidAt;
}
