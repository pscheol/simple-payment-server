package com.devpaik.payment.primary.api.dto;

public record PaymentDetail(
        String cardNumber,
        String expiryDate,
        String cvv
) {
}
