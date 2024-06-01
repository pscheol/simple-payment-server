package com.devpaik.payment.adapter.out.persistence.user;

import com.devpaik.payment.adapter.out.persistence.user.repository.WalletRepository;
import com.devpaik.payment.application.port.out.user.GetWalletInfoPort;
import com.devpaik.payment.domain.user.Wallet;
import com.devpaik.payment.exception.NotFoundWalletException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class WalletRepositoryAdapter implements GetWalletInfoPort {
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    @Override
    public List<Wallet> getMyWalletInfo(String userId) {
        return walletRepository.findByUser(userId)
                .orElseThrow(() -> new NotFoundWalletException("지갑이 존재하지 않습니다."))
                .stream()
                .map(walletMapper::mapToWallet)
                .collect(Collectors.toList());
    }

    @Override
    public Wallet getMyWalletInfoByCurrency(String userId, String currencyCode) {
        return walletRepository.findByUserCurrencyCode(userId, currencyCode)
                .map(walletMapper::mapToWallet)
                .orElseThrow(() -> new NotFoundWalletException("존재하지 않는 지갑입니다. Currency : " + currencyCode));
    }
}
