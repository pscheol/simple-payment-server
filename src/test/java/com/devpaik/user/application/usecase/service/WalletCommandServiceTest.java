package com.devpaik.user.application.usecase.service;

import com.devpaik.user.application.output.UpdateWalletPort;
import com.devpaik.user.application.usecase.command.WithdrawalBalanceCommand;
import com.devpaik.user.domain.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
class WalletCommandServiceTest {

    private final UpdateWalletPort updateWalletPort = Mockito.mock(UpdateWalletPort.class);
    private final WalletCommandService walletCommandService = new WalletCommandService(updateWalletPort);


    @Test
    public void withdrawalBalanceTest() {

        Wallet wallet = Wallet.createWallet("wallet1", "userId1234", "USD", new BigDecimal("10.00"));
        WithdrawalBalanceCommand command = new WithdrawalBalanceCommand(wallet);

        walletCommandService.withdrawalBalance(command);
    }
}