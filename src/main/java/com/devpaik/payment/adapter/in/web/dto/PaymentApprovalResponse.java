package com.devpaik.payment.adapter.in.web.dto;

import com.devpaik.payment.domain.payment.PaymentApproval;

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
                approval.getPaymentDtm().getDatetime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }
}
