package com.devpaik.exchangerate.application.usecase.service;

import com.devpaik.exchangerate.application.output.GetDailyExchangeRatePort;
import com.devpaik.exchangerate.domain.DailyExchangeRate;
import com.devpaik.exchangerate.domain.field.CurrencyCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@Service
public class ExchangeRateQueryService implements DailyExchangeRateUseCase {
    private final GetDailyExchangeRatePort getDailyExchangeRatePort;

    @Transactional(readOnly = true)
    @Override
    public DailyExchangeRate getDailyExchangeRate(String currencyCode) {
        if (CurrencyCode.KRW.equals(currencyCode)) {
            return DailyExchangeRate.emptyInstance();
        }
        LocalDateTime startDtm = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return getDailyExchangeRatePort.getLastExchangeRate(currencyCode, startDtm);
    }
}
