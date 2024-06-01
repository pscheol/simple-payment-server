package com.devpaik.payment.adapter.out.persistence.exchangerate;

import com.devpaik.payment.adapter.out.persistence.exchangerate.entity.DailyExchangeRateEntity;
import com.devpaik.payment.domain.exchangerate.DailyExchangeRate;
import org.springframework.stereotype.Component;

@Component
public class DailyExchangeRateMapper {

    public DailyExchangeRate mapToDailyExchangeRate(DailyExchangeRateEntity entity) {
        return DailyExchangeRate.createDailyExchangeRateByEntity(entity);
    }
}
