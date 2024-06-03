package com.devpaik.payment.application.port.out.payment;

import com.devpaik.payment.domain.payment.field.CardNum;

import java.util.concurrent.CompletableFuture;

public interface SendCreditCardApprovalPort {

    CompletableFuture<String> sendApproval(CardNum cardNum, String cvv, String expiryDate);
}
