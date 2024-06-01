package com.devpaik.payment.adapter.in.web.dto;

import com.devpaik.payment.domain.exchangerate.field.CurrencyCode.Code;
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
