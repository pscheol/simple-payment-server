package com.devpaik.payment.adapter.in.web.dto;

import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.user.Wallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record WalletView(String userId, BigDecimal balance, String currency) {

    public static List<WalletView> toBuild(List<Wallet> wallets) {
        return wallets.stream().map(wallet -> {
            CurrencyCode currencyCode = wallet.getCurrencyCode();
            return new WalletView(wallet.getUserId().getId(), wallet.getBalance().scaleValue(currencyCode), currencyCode.getValue());
        }).collect(Collectors.toList());
    }
}
