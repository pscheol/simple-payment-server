package com.devpaik.payment.adapter.out.persistence.exchangerate;

import com.devpaik.payment.adapter.out.persistence.exchangerate.entity.CurrencyEntity;
import com.devpaik.payment.adapter.out.persistence.exchangerate.entity.DailyExchangeRateEntity;
import com.devpaik.payment.adapter.out.persistence.exchangerate.repository.CurrencyRepository;
import com.devpaik.payment.adapter.out.persistence.exchangerate.repository.DailyExchangeRateRepository;
import com.devpaik.payment.domain.exchangerate.DailyExchangeRate;
import com.devpaik.payment.exception.NotFoundDailyExchangeRateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({DailyExchangeRateRepositoryAdapter.class, DailyExchangeRateMapper.class})
class DailyExchangeRateRepositoryAdapterTest {

    @Autowired
    private DailyExchangeRateRepositoryAdapter dailyExchangeRateRepositoryAdapter;

    @Autowired
    private DailyExchangeRateMapper dailyExchangeRateMapper;


    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private DailyExchangeRateRepository dailyExchangeRateRepository;


    private final LocalDateTime nowDtm = LocalDateTime.now();
    @BeforeEach
    public void init() {

        CurrencyEntity currency = currencyRepository.save(CurrencyEntity.builder()
                        .currencyCode("USD")
                        .currencyName("달러")
                .build());

        dailyExchangeRateRepository.save(DailyExchangeRateEntity.builder()
                        .currency(currency)
                        .exchangeRate(new BigDecimal("1370.55"))
                        .currentDtm(nowDtm)
                .build());

        dailyExchangeRateRepository.save(DailyExchangeRateEntity.builder()
                .currency(currency)
                .exchangeRate(new BigDecimal("1310.12"))
                .currentDtm(nowDtm.minusHours(5))
                .build());
    }


    @DisplayName("최종 공시환율 정보 조회")
    @Test
    public void getLastDailyExchangeRateTest() {
        LocalDateTime startDtm = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        DailyExchangeRate exchangeRate = dailyExchangeRateRepositoryAdapter.getLastExchangeRate("USD", startDtm);

        assertNotNull(exchangeRate);
        assertEquals(1L, exchangeRate.getDailyId().getId());
        assertEquals("USD", exchangeRate.getCurrencyCode().getValue());
        assertEquals(new BigDecimal("1370.55"), exchangeRate.getExchangeRate().getValue());
        assertEquals(nowDtm, exchangeRate.getCurrentDtm().getDatetime());
    }

    @DisplayName("최종 공시환율 정보 조회 오류")
    @Test
    public void exceptionLastDailyExchangeRateTest() {
        LocalDateTime startDtm = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        assertThrows(NotFoundDailyExchangeRateException.class, () ->
            dailyExchangeRateRepositoryAdapter.getLastExchangeRate("KRW", startDtm));
    }
}