package com.devpaik.exchangerate.secondary.jpa;

import com.devpaik.exchangerate.application.output.GetDailyExchangeRatePort;
import com.devpaik.exchangerate.domain.DailyExchangeRate;
import com.devpaik.exchangerate.secondary.jpa.repository.DailyExchangeRateRepository;
import com.devpaik.payment.exception.NotFoundDailyExchangeRateException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@Component
public class DailyExchangeRateRepositoryAdapter implements GetDailyExchangeRatePort {

    private final DailyExchangeRateRepository dailyExchangeRateRepository;
    private final DailyExchangeRateMapper dailyExchangeRateMapper;

    @Override
    public DailyExchangeRate getLastExchangeRate(String currencyCode, LocalDateTime startDtm) {
        Sort sort = Sort.by(Sort.Direction.DESC, "currentDtm");
        PageRequest pageRequest = PageRequest.of(0, 1, sort);

        return dailyExchangeRateRepository.findByCurrencyCurrencyCodeAndCurrentDtm(currencyCode, startDtm, pageRequest)
                .stream().findFirst()
                .map(dailyExchangeRateMapper::mapToDailyExchangeRate)
                .orElseThrow(() -> new NotFoundDailyExchangeRateException("존재하지 않는 환율 정보 입니다. currency :" + currencyCode));
    }
}
