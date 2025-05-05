package com.devpaik.payment.primary.api.dto;

import com.devpaik.exchangerate.domain.field.CurrencyCode.Code;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentEstimateRequest(
        @Positive
        BigDecimal amount,

        @NotNull
        Code currency,

        @NotBlank
        String merchantId,

        @NotBlank
        String userId) {
}
