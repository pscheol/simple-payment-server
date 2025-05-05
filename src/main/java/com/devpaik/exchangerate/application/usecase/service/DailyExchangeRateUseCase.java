package com.devpaik.exchangerate.application.usecase.service;

import com.devpaik.exchangerate.domain.DailyExchangeRate;

public interface DailyExchangeRateUseCase {
    DailyExchangeRate getDailyExchangeRate(String currencyCode);
}
