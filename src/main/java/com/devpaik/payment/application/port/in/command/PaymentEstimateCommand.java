package com.devpaik.payment.application.port.in.command;

import com.devpaik.payment.adapter.in.web.dto.PaymentEstimateRequest;
import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.payment.field.Amount;

public record PaymentEstimateCommand(Amount amount, CurrencyCode currencyCode) {

    public static PaymentEstimateCommand of(PaymentEstimateRequest request) {
        CurrencyCode currencyCode = CurrencyCode.createCurrencyCode(request.currency());
        return new PaymentEstimateCommand(new Amount(request.amount()), currencyCode);
    }
}
