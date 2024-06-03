package com.devpaik.payment.application.port.out.user;

import com.devpaik.payment.domain.user.Wallet;

public interface UpdateWalletPort {
    Wallet withdrawalBalance(Wallet withdrawWallet);
}
