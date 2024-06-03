package com.devpaik.payment.application.port.in.command;

import com.devpaik.payment.domain.payment.field.CardNum;

public record PaymentDetailCommand(
        CardNum cardNum,
        String expiryDate,
        String cvv
) {
}
