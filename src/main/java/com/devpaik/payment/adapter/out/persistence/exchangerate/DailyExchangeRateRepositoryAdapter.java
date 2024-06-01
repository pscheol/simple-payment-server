package com.devpaik.payment.adapter.out.persistence.exchangerate;

import com.devpaik.payment.adapter.out.persistence.exchangerate.repository.DailyExchangeRateRepository;
import com.devpaik.payment.application.port.out.exchangerate.GetDailyExchangeRatePort;
import com.devpaik.payment.domain.exchangerate.DailyExchangeRate;
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
