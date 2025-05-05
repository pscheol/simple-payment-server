package com.devpaik.payment.application.output;

import com.devpaik.payment.domain.field.CardNum;

import java.util.concurrent.CompletableFuture;

public interface SendCreditCardApprovalPort {

    CompletableFuture<String> sendApproval(CardNum cardNum, String cvv, String expiryDate);
}
