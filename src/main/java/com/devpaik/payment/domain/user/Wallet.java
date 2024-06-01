package com.devpaik.payment.domain.user;

import com.devpaik.payment.adapter.out.persistence.user.entity.WalletEntity;
import com.devpaik.payment.domain.user.field.Balance;
import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.user.field.UserId;
import com.devpaik.payment.domain.user.field.WalletId;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


public class Wallet implements Serializable {
    @Getter
    private final WalletId walletId;

    @Getter
    private final UserId userId;

    @Getter
    private final CurrencyCode currencyCode;

    @Getter
    private final Balance balance;


    private Wallet(WalletId walletId, UserId userId, CurrencyCode currencyCode, Balance balance) {
        this.walletId = walletId;
        this.userId = userId;
        this.currencyCode = currencyCode;
        this.balance = balance;
    }


    public static Wallet createWalletByEntity(WalletEntity entity) {
        return new Wallet(
                new WalletId(entity.getWalletId()),
                new UserId(entity.getUser().getUserId()),
                new CurrencyCode(entity.getCurrencyCode()),
                new Balance(entity.getBalance())
        );
    }

    public static Wallet createWallet(String walletId, String userId, String currencyCode, BigDecimal balance) {
        return new Wallet(
                new WalletId(walletId),
                new UserId(userId),
                new CurrencyCode(currencyCode),
                new Balance(balance)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(walletId, wallet.walletId) && Objects.equals(userId, wallet.userId) &&
                Objects.equals(currencyCode, wallet.currencyCode) && Objects.equals(balance, wallet.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId, userId, currencyCode, balance);
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletId=" + walletId +
                ", userId=" + userId +
                ", currencyCode=" + currencyCode +
                ", balance=" + balance +
                '}';
    }
}
