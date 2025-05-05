package com.devpaik.merchant.secondary.jpa.entity;

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
@Table(name = "tb_merchant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@DynamicInsert
public class MerchantEntity implements Serializable {
    @Id
    @Column(name = "merchant_id", nullable = false)
    @Comment("상점ID")
    @Getter
    private String merchantId;

    @Column(name = "merchant_name", length = 200)
    @Comment("상점명")
    @Getter
    private String merchantName;

    @Builder
    public MerchantEntity(String merchantId, String merchantName) {
        this.merchantId = merchantId;
        this.merchantName = merchantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantEntity that = (MerchantEntity) o;
        return Objects.equals(merchantId, that.merchantId) && Objects.equals(merchantName, that.merchantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchantId, merchantName);
    }

    @Override
    public String toString() {
        return "MerchantEntity{" +
                "merchantId=" + merchantId +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}
