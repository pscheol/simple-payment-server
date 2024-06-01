package com.devpaik.payment.adapter.out.persistence.exchangerate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_currency")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@DynamicInsert
public class CurrencyEntity implements Serializable {

    @Id
    @Column(name = "currency_code", length = 3, nullable = false)
    @Comment("통화코드")
    @Getter
    private String currencyCode;

    @Comment("통화명")
    @Column(name = "currency_name", length = 30)
    @Getter
    private String currencyName;

    @Builder
    public CurrencyEntity(String currencyCode, String currencyName) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyEntity that = (CurrencyEntity) o;
        return Objects.equals(currencyCode, that.currencyCode) && Objects.equals(currencyName, that.currencyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyCode, currencyName);
    }

    @Override
    public String toString() {
        return "CurrencyCodeEntity{" +
                "currencyCode='" + currencyCode + '\'' +
                ", currencyName='" + currencyName + '\'' +
                '}';
    }
}
