package com.devpaik.user.application.usecase.service;

import com.devpaik.user.application.output.GetWalletInfoPort;
import com.devpaik.user.application.usecase.command.MyWalletCommand;
import com.devpaik.user.domain.Wallet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
class WalletQueryServiceTest {

    private final GetWalletInfoPort getWalletInfoPort = Mockito.mock(GetWalletInfoPort.class);
    private final WalletQueryService walletQueryService = new WalletQueryService(getWalletInfoPort);


    @Test
    public void getMyWalletTest() {
        MyWalletCommand myWalletCommand = new MyWalletCommand("userId1234", "USD");

        Wallet walletExtract = Wallet.createWallet("wallet1", "userId1234", "USD", new BigDecimal("1000.00"));


        BDDMockito.given(walletQueryService.getMyWallet(myWalletCommand)).willReturn(walletExtract);

        Wallet wallet = walletQueryService.getMyWallet(myWalletCommand);

        Assertions.assertThat(wallet).isEqualTo(walletExtract);
    }
}