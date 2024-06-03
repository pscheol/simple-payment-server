package com.devpaik.payment.adapter.out.persistence.user;

import com.devpaik.payment.adapter.out.persistence.user.entity.UserEntity;
import com.devpaik.payment.adapter.out.persistence.user.entity.WalletEntity;
import com.devpaik.payment.domain.user.Wallet;
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
