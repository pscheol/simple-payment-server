package com.devpaik.user.application.usecase.service;

import com.devpaik.user.application.output.UpdateWalletPort;
import com.devpaik.user.application.usecase.command.WithdrawalBalanceCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class WalletCommandService implements WithdrawalBalanceUseCase {

    private final UpdateWalletPort updateWalletPort;


    @Transactional
    @Override
    public void withdrawalBalance(WithdrawalBalanceCommand command) {
        updateWalletPort.withdrawalBalance(command.wallet());
    }

}
