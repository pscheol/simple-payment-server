package com.devpaik.payment.application.provider;

import com.devpaik.exchangerate.domain.DailyExchangeRate;

public interface ExchangeRateProvider {
    DailyExchangeRate getDailyExchangeRate(String currencyCode);
}
