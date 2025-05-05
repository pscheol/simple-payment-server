package com.devpaik.payment.primary.api.dto;

import com.devpaik.payment.domain.PaymentApproval;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public record PaymentApprovalResponse(
    String paymentId,
    String status,
    BigDecimal amountTotal,
    String currency,
    String timestamp
) {

    public static PaymentApprovalResponse toBuild(PaymentApproval approval) {
        return new PaymentApprovalResponse(
                approval.getPaymentId().getId(),
                approval.getStatus().toString(),
                approval.getAmountTotalScale().getValue(),
                approval.getCurrencyCode().getValue(),
                approval.getPaymentDtm().getDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
        );
    }
}
