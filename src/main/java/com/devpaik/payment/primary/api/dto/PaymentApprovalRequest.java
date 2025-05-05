package com.devpaik.payment.primary.api.dto;

import com.devpaik.exchangerate.domain.field.CurrencyCode;
import com.devpaik.payment.domain.field.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentApprovalRequest(
        @NotBlank
        String userId,

        @Positive
        BigDecimal amount,

        @NotNull
        CurrencyCode.Code currency,
        @NotBlank
        String merchantId,

        @NotNull
        PaymentMethod paymentMethod,

        PaymentDetail paymentDetail
) {
}
