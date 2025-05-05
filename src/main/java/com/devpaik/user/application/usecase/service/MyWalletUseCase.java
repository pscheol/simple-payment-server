package com.devpaik.user.application.usecase.service;

import com.devpaik.user.application.usecase.command.MyWalletCommand;
import com.devpaik.user.domain.Wallet;

public interface MyWalletUseCase {
    Wallet getMyWallet(MyWalletCommand myWalletCommand);
}
