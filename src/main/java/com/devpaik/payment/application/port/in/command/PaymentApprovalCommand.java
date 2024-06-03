package com.devpaik.payment.application.port.in.command;

import com.devpaik.payment.adapter.in.web.dto.PaymentApprovalRequest;
import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.merchant.field.MerchantId;
import com.devpaik.payment.domain.payment.field.Amount;
import com.devpaik.payment.domain.payment.field.CardNum;
import com.devpaik.payment.domain.payment.field.PaymentMethod;
import com.devpaik.payment.domain.user.field.UserId;

import java.util.Objects;

public record PaymentApprovalCommand(
        UserId userId,
        Amount amount,
        CurrencyCode currencyCode,
        MerchantId merchantId,
        PaymentMethod paymentMethod,
        PaymentDetailCommand paymentDetail
) {

    public static PaymentApprovalCommand of(PaymentApprovalRequest request) {
        PaymentDetailCommand detailCommand = null;
        if (Objects.nonNull(request.paymentDetail())) {
            detailCommand = new PaymentDetailCommand(
                    new CardNum(request.paymentDetail().cardNumber()),
                    request.paymentDetail().expiryDate(),
                    request.paymentDetail().cvv()
                    );
        }

        return new PaymentApprovalCommand(new UserId(
                request.userId()),
                new Amount(request.amount()),
                CurrencyCode.createCurrencyCode(request.currency()),
                new MerchantId(request.merchantId()),
                request.paymentMethod(),
                detailCommand
        );
    }
}
