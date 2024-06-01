package com.devpaik.payment.application.port.out.user;

import com.devpaik.payment.domain.user.Wallet;

import java.util.List;

public interface GetWalletInfoPort {

    List<Wallet> getMyWalletInfo(String userId);
    Wallet getMyWalletInfoByCurrency(String userId, String currencyCode);
}
