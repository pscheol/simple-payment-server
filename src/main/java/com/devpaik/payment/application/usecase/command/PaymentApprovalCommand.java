package com.devpaik.payment.application.usecase.command;

import com.devpaik.exchangerate.domain.field.CurrencyCode;
import com.devpaik.merchant.domain.field.MerchantId;
import com.devpaik.payment.domain.field.Amount;
import com.devpaik.payment.domain.field.CardNum;
import com.devpaik.payment.domain.field.PaymentMethod;
import com.devpaik.payment.primary.api.dto.PaymentApprovalRequest;
import com.devpaik.user.domain.field.UserId;

import java.time.ZoneId;
import java.util.Objects;

public record PaymentApprovalCommand(
        UserId userId,
        Amount amount,
        CurrencyCode currencyCode,
        MerchantId merchantId,
        PaymentMethod paymentMethod,
        PaymentDetailCommand paymentDetail,
        ZoneId zoneId) {

    public static PaymentApprovalCommand of(PaymentApprovalRequest request, ZoneId zoneId) {
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
                detailCommand,
                zoneId
        );
    }
}
