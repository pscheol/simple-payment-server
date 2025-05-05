package com.devpaik.payment.application.usecase.command;

import com.devpaik.payment.domain.field.CardNum;

public record PaymentDetailCommand(
        CardNum cardNum,
        String expiryDate,
        String cvv
) {
}
