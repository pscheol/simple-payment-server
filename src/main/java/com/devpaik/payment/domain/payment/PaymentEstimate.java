package com.devpaik.payment.domain.payment;

import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.payment.field.Amount;
import com.devpaik.payment.domain.payment.field.Fee;

import java.math.BigDecimal;

public record PaymentEstimate(Amount estimatedTotal, Fee fees, CurrencyCode currency) {


    public BigDecimal parseEstimateTotal() {
        return estimatedTotal.scaleValue(currency);
    }
    public BigDecimal parseFee() {
        return fees.scaleValue();
    }
    public String parseCurrencyCode() {
        return currency.getValue();
    }
}

