package com.devpaik.user.secondary.jpa;

import com.devpaik.user.application.output.GetWalletInfoPort;
import com.devpaik.user.application.output.UpdateWalletPort;
import com.devpaik.user.domain.Wallet;
import com.devpaik.user.exception.NotFoundWalletException;
import com.devpaik.user.secondary.jpa.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class WalletRepositoryAdapter implements GetWalletInfoPort, UpdateWalletPort {
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    @Transactional(readOnly = true)
    @Override
    public List<Wallet> getMyWalletInfo(String userId) {
        return walletRepository.findByUser(userId)
                .orElseThrow(() -> new NotFoundWalletException("지갑이 존재하지 않습니다."))
                .stream()
                .map(walletMapper::mapToWallet)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Wallet getMyWalletInfoByCurrency(String userId, String currencyCode) {
        return walletRepository.findByUserCurrencyCode(userId, currencyCode)
                .map(walletMapper::mapToWallet)
                .orElseThrow(() -> new NotFoundWalletException("존재하지 않는 지갑입니다. Currency : " + currencyCode));
    }

    @Override
    public Wallet withdrawalBalance(Wallet withdrawWallet) {
        return walletMapper.mapToWallet(walletRepository.save(walletMapper.mapToWalletEntity(withdrawWallet)));
    }
}
