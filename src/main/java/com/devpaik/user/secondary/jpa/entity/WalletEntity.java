package com.devpaik.user.secondary.jpa.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tb_wallet")
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class WalletEntity implements Serializable {

    @Id
    @Column(name = "wallet_id", length = 60, nullable = false)
    @Comment("지갑ID")
    @Getter
    private String walletId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @Comment("사용자ID")
    @Getter
    private UserEntity user;

    @Column(name = "currency_code", length = 3)
    @Comment("통화코드")
    @Getter
    private String currencyCode;

    @Column(name = "balnace", precision = 18, scale = 2)
    @Comment("잔액")
    @Getter
    private BigDecimal balance;

    @Builder
    public WalletEntity(String walletId, UserEntity user, String currencyCode, BigDecimal balance) {
        this.walletId = walletId;
        this.user = user;
        this.currencyCode = currencyCode;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalletEntity that = (WalletEntity) o;
        return Objects.equals(walletId, that.walletId) && Objects.equals(user, that.user) &&
                Objects.equals(currencyCode, that.currencyCode) && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId, user, currencyCode, balance);
    }

    @Override
    public String toString() {
        return "WalletEntity{" +
                "walletId='" + walletId + '\'' +
                ", user=" + (Objects.isNull(user) ? null : user.getUserId()) +
                ", currencyCode='" + currencyCode + '\'' +
                ", balance=" + balance +
                '}';
    }
}
