package com.devpaik.payment.application.usecase.command;

import com.devpaik.exchangerate.domain.field.CurrencyCode;
import com.devpaik.payment.domain.field.Amount;
import com.devpaik.payment.primary.api.dto.PaymentEstimateRequest;

public record PaymentEstimateCommand(Amount amount, CurrencyCode currencyCode) {

    public static PaymentEstimateCommand of(PaymentEstimateRequest request) {
        CurrencyCode currencyCode = CurrencyCode.createCurrencyCode(request.currency());
        return new PaymentEstimateCommand(new Amount(request.amount()), currencyCode);
    }
}
