package com.devpaik.payment.adapter.in.web.dto;

public record PaymentDetail(
        String cardNumber,
        String expiryDate,
        String cvv
) {
}
