package com.devpaik.exchangerate.application.usecase.service;

import com.devpaik.exchangerate.application.output.GetDailyExchangeRatePort;
import com.devpaik.exchangerate.domain.DailyExchangeRate;
import com.devpaik.exchangerate.domain.field.CurrencyCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ExtendWith(SpringExtension.class)
class ExchangeRateQueryServiceTest {

    private final GetDailyExchangeRatePort getDailyExchangeRatePort = Mockito.mock(GetDailyExchangeRatePort.class);

    private final ExchangeRateQueryService exchangeRateQueryService = new ExchangeRateQueryService(
            getDailyExchangeRatePort
    );

    @DisplayName("당일 환율 정보")
    @Test
    public void getDailyExchangeRateTest() {
        LocalDateTime startDtm = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        DailyExchangeRate exchangeRateExtract = DailyExchangeRate.createDailyExchangeRate(1L, "USD",
                new BigDecimal("1482.52"), startDtm);

        BDDMockito.given(exchangeRateQueryService.getDailyExchangeRate(CurrencyCode.USD))
                .willReturn(exchangeRateExtract);

        DailyExchangeRate exchangeRate = exchangeRateQueryService.getDailyExchangeRate(CurrencyCode.USD);

        Assertions.assertThat(exchangeRate).isEqualTo(exchangeRateExtract);
    }
}