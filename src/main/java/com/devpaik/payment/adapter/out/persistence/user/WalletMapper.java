package com.devpaik.payment.adapter.out.persistence.user;

import com.devpaik.payment.adapter.out.persistence.user.entity.WalletEntity;
import com.devpaik.payment.domain.user.Wallet;
import org.springframework.stereotype.Component;

@Component
class WalletMapper {

    public Wallet mapToWallet(WalletEntity entity) {
        return Wallet.createWalletByEntity(entity);
    }
}
