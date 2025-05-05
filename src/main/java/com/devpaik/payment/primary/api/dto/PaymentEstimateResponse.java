package com.devpaik.payment.primary.api.dto;

import com.devpaik.payment.domain.PaymentEstimate;

import java.math.BigDecimal;

public record PaymentEstimateResponse(BigDecimal estimatedTotal, BigDecimal fees, String currency) {

    public static PaymentEstimateResponse toBuild(PaymentEstimate estimate) {

        return new PaymentEstimateResponse(estimate.parseEstimateTotal(), estimate.parseFee(), estimate.parseCurrencyCode());
    }
}

