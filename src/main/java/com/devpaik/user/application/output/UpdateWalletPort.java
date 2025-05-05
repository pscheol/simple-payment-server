package com.devpaik.user.application.output;

import com.devpaik.user.domain.Wallet;

public interface UpdateWalletPort {
    Wallet withdrawalBalance(Wallet withdrawWallet);
}
