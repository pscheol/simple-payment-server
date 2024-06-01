package com.devpaik.payment.domain.merchant;

import com.devpaik.payment.domain.merchant.field.MerchantId;
import com.devpaik.payment.domain.merchant.field.MerchantName;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

public class Merchant implements Serializable {
    @Getter
    private final MerchantId merchantId;

    @Getter
    private final MerchantName merchantName;

    public Merchant(MerchantId merchantId, MerchantName merchantName) {
        this.merchantId = merchantId;
        this.merchantName = merchantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Merchant merchant = (Merchant) o;
        return Objects.equals(merchantId, merchant.merchantId) && Objects.equals(merchantName, merchant.merchantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchantId, merchantName);
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "merchantId=" + merchantId +
                ", merchantName=" + merchantName +
                '}';
    }
}
