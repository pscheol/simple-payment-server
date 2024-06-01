package com.devpaik.payment.adapter.in.web.dto;

import com.devpaik.payment.domain.payment.PaymentEstimate;

import java.math.BigDecimal;

public record PaymentEstimateResponse(BigDecimal estimatedTotal, BigDecimal fees, String currency) {

    public static PaymentEstimateResponse toBuild(PaymentEstimate estimate) {

        return new PaymentEstimateResponse(estimate.parseEstimateTotal(), estimate.parseFee(), estimate.parseCurrencyCode());
    }
}

