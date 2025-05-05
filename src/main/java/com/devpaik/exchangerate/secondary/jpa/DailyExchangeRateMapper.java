package com.devpaik.exchangerate.secondary.jpa;

import com.devpaik.exchangerate.domain.DailyExchangeRate;
import com.devpaik.exchangerate.secondary.jpa.entity.DailyExchangeRateEntity;
import org.springframework.stereotype.Component;

@Component
public class DailyExchangeRateMapper {

    public DailyExchangeRate mapToDailyExchangeRate(DailyExchangeRateEntity entity) {
        return DailyExchangeRate.createDailyExchangeRateByEntity(entity);
    }
}
