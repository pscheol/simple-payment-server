package com.devpaik.exchangerate.domain;

import com.devpaik.exchangerate.domain.field.CurrencyCode;
import com.devpaik.exchangerate.domain.field.CurrentDtm;
import com.devpaik.exchangerate.domain.field.DailyId;
import com.devpaik.exchangerate.domain.field.ExchangeRate;
import com.devpaik.exchangerate.secondary.jpa.entity.DailyExchangeRateEntity;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class DailyExchangeRate implements Serializable {
    @Getter
    private final DailyId dailyId;

    @Getter
    private final CurrencyCode currencyCode;

    @Getter
    private final ExchangeRate exchangeRate;

    @Getter
    private final CurrentDtm currentDtm;

    private DailyExchangeRate() {
        this(null, null, null, null);
    }

    private DailyExchangeRate(DailyId dailyId, CurrencyCode currencyCode, ExchangeRate exchangeRate, CurrentDtm currentDtm) {
        this.dailyId = dailyId;
        this.currencyCode = currencyCode;
        this.exchangeRate = exchangeRate;
        this.currentDtm = currentDtm;
    }


    public static DailyExchangeRate createDailyExchangeRate(Long dailyId, String currencyCode, BigDecimal exchangeRate, LocalDateTime currentDtm) {
        return new DailyExchangeRate(
                new DailyId(dailyId),
                new CurrencyCode(currencyCode),
                new ExchangeRate(exchangeRate),
                new CurrentDtm(currentDtm)
        );

    }
    public static DailyExchangeRate emptyInstance() {
        return new DailyExchangeRate();
    }

    public static DailyExchangeRate createDailyExchangeRateByEntity(DailyExchangeRateEntity entity) {
        return new DailyExchangeRate(
                new DailyId(entity.getDailyId()),
                new CurrencyCode(entity.getCurrency().getCurrencyCode()),
                new ExchangeRate(entity.getExchangeRate()),
                new CurrentDtm(entity.getCurrentDtm())
                );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyExchangeRate that = (DailyExchangeRate) o;
        return Objects.equals(dailyId, that.dailyId) && Objects.equals(currencyCode, that.currencyCode) && Objects.equals(exchangeRate, that.exchangeRate) && Objects.equals(currentDtm, that.currentDtm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dailyId, currencyCode, exchangeRate, currentDtm);
    }

    @Override
    public String toString() {
        return "DailyExchangeRate{" +
                "dailyId=" + dailyId +
                ", currencyCode=" + currencyCode +
                ", exchangeRate=" + exchangeRate +
                ", currentDtm=" + currentDtm +
                '}';
    }
}
