package com.devpaik.exchangerate.application.output;

import com.devpaik.exchangerate.domain.DailyExchangeRate;

import java.time.LocalDateTime;

public interface GetDailyExchangeRatePort {

    public DailyExchangeRate getLastExchangeRate(String currencyCode, LocalDateTime startDtm);
}
