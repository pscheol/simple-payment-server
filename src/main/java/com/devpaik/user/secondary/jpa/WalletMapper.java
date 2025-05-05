package com.devpaik.user.secondary.jpa;

import com.devpaik.user.domain.Wallet;
import com.devpaik.user.secondary.jpa.entity.UserEntity;
import com.devpaik.user.secondary.jpa.entity.WalletEntity;
import org.springframework.stereotype.Component;

@Component
class WalletMapper {

    public Wallet mapToWallet(WalletEntity entity) {
        return Wallet.createWalletByEntity(entity);
    }

    public WalletEntity mapToWalletEntity(Wallet wallet) {
        return WalletEntity.builder()
                .walletId(wallet.getWalletId().getId())
                .user(UserEntity.builder().userId(wallet.getUserId().getId()).build())
                .currencyCode(wallet.getCurrencyCode().getValue())
                .balance(wallet.getBalance().getValue())
                .build();
    }
}
