package com.devpaik.user.application.output;

import com.devpaik.user.domain.Wallet;

import java.util.List;

public interface GetWalletInfoPort {

    List<Wallet> getMyWalletInfo(String userId);
    Wallet getMyWalletInfoByCurrency(String userId, String currencyCode);
}
