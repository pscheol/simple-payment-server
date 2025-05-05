package com.devpaik.user.application.usecase.event;

import com.devpaik.payment.domain.event.WithdrawalBalanceEvent;
import com.devpaik.user.application.usecase.command.WithdrawalBalanceCommand;
import com.devpaik.user.application.usecase.service.WithdrawalBalanceUseCase;
import com.devpaik.user.domain.Wallet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class WalletEventHandler {
    private final WithdrawalBalanceUseCase withdrawalBalanceUseCase;

    @EventListener(WithdrawalBalanceEvent.class)
    public void handle(WithdrawalBalanceEvent event) {
        log.info("Received withdrawal balance event: {}", event);
        Wallet wallet = Wallet.createWallet(event.walletId(), event.userId(), event.currencyCode(), event.balance());

        withdrawalBalanceUseCase.withdrawalBalance(new WithdrawalBalanceCommand(wallet));
    }
}
