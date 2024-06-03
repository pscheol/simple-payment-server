package com.devpaik.payment.adapter.in.web.dto;

import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.payment.field.PaymentMethod;
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
