package com.devpaik.payment.secondary.bc.wallet;

import com.devpaik.exchangerate.domain.field.CurrencyCode;
import com.devpaik.payment.application.provider.WalletProvider;
import com.devpaik.user.application.usecase.command.MyWalletCommand;
import com.devpaik.user.application.usecase.service.MyWalletUseCase;
import com.devpaik.user.domain.Wallet;
import com.devpaik.user.domain.field.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WalletAdapter implements WalletProvider {

    private final MyWalletUseCase myWalletUseCase;

    @Override
    public Wallet getMyWallet(UserId userId, CurrencyCode currencyCode) {
        return myWalletUseCase.getMyWallet(new MyWalletCommand(userId.getId(), currencyCode.getValue()));
    }
}
