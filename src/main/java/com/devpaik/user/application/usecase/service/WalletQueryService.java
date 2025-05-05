package com.devpaik.user.application.usecase.service;

import com.devpaik.user.application.output.GetWalletInfoPort;
import com.devpaik.user.application.usecase.command.MyWalletCommand;
import com.devpaik.user.domain.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class WalletQueryService implements MyWalletUseCase {
    private final GetWalletInfoPort getWalletInfoPort;

    @Transactional
    @Override
    public Wallet getMyWallet(MyWalletCommand myWalletCommand) {
        return getWalletInfoPort.getMyWalletInfoByCurrency(myWalletCommand.userId(), myWalletCommand.currencyCode());
    }
}
