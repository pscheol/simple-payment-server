package com.devpaik.payment.adapter.out.persistence.exchangerate.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_daily_exchange_rate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@DynamicInsert
public class DailyExchangeRateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_id", nullable = false)
    @Comment("일별 SEQ")
    @Getter
    private Long dailyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_code", referencedColumnName = "currency_code", nullable = false)
    @Comment("통화코드")
    @Getter
    private CurrencyEntity currency;


    @Column(name = "exchange_rate", precision = 18, scale = 2)
    @Comment("현재환율")
    @Getter
    private BigDecimal exchangeRate;

    @Column(name = "current_dtm")
    @Comment("공시날짜")
    @Getter
    private LocalDateTime currentDtm;

    @Builder
    public DailyExchangeRateEntity(Long dailyId, CurrencyEntity currency, BigDecimal exchangeRate,
                                   LocalDateTime currentDtm) {
        this.dailyId = dailyId;
        this.currency = currency;
        this.exchangeRate = exchangeRate;
        this.currentDtm = currentDtm;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyExchangeRateEntity that = (DailyExchangeRateEntity) o;
        return Objects.equals(dailyId, that.dailyId) && Objects.equals(currency, that.currency) &&
                Objects.equals(exchangeRate, that.exchangeRate) && Objects.equals(currentDtm, that.currentDtm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dailyId, currency, exchangeRate, currentDtm);
    }

    @Override
    public String toString() {
        return "DailyExchangeRateEntity{" +
                "dailyId=" + dailyId +
                ", currencyCode=" + (Objects.isNull(currency) ? null : currency.getCurrencyCode()) +
                ", exchangeRate=" + exchangeRate +
                ", currentDtm=" + currentDtm +
                '}';
    }
}
