package com.devpaik.user.application.usecase.service;

import com.devpaik.user.application.usecase.command.WithdrawalBalanceCommand;

public interface WithdrawalBalanceUseCase {
    public void withdrawalBalance(WithdrawalBalanceCommand withdrawalBalanceCommand);
}
