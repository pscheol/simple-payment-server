package com.devpaik.payment.application.provider;

import com.devpaik.exchangerate.domain.field.CurrencyCode;
import com.devpaik.user.domain.Wallet;
import com.devpaik.user.domain.field.UserId;

public interface WalletProvider {
    Wallet getMyWallet(UserId userId, CurrencyCode currencyCode);
}
