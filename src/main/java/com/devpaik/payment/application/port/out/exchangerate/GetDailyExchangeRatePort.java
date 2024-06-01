package com.devpaik.payment.application.port.out.exchangerate;

import com.devpaik.payment.domain.exchangerate.DailyExchangeRate;

import java.time.LocalDateTime;

public interface GetDailyExchangeRatePort {

    public DailyExchangeRate getLastExchangeRate(String currencyCode, LocalDateTime startDtm);
}
