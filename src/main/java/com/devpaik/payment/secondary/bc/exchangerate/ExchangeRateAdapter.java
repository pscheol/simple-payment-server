package com.devpaik.payment.secondary.bc.exchangerate;

import com.devpaik.exchangerate.application.usecase.service.DailyExchangeRateUseCase;
import com.devpaik.exchangerate.domain.DailyExchangeRate;
import com.devpaik.payment.application.provider.ExchangeRateProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExchangeRateAdapter implements ExchangeRateProvider {
    private final DailyExchangeRateUseCase dailyExchangeRateUseCase;

    @Override
    public DailyExchangeRate getDailyExchangeRate(String currencyCode) {
        return dailyExchangeRateUseCase.getDailyExchangeRate(currencyCode);
    }
}
