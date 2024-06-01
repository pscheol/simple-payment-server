package com.devpaik.payment.domain.exchangerate;

import com.devpaik.payment.adapter.out.persistence.exchangerate.entity.DailyExchangeRateEntity;
import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.exchangerate.field.CurrentDtm;
import com.devpaik.payment.domain.exchangerate.field.DailyId;
import com.devpaik.payment.domain.exchangerate.field.ExchangeRate;
import lombok.Getter;

import java.io.Serializable;
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

    private DailyExchangeRate(DailyId dailyId, CurrencyCode currencyCode, ExchangeRate exchangeRate, CurrentDtm currentDtm) {
        this.dailyId = dailyId;
        this.currencyCode = currencyCode;
        this.exchangeRate = exchangeRate;
        this.currentDtm = currentDtm;
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
