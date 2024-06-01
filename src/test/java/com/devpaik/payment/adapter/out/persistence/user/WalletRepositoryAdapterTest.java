package com.devpaik.payment.adapter.out.persistence.user;

import com.devpaik.payment.adapter.out.persistence.user.entity.UserEntity;
import com.devpaik.payment.adapter.out.persistence.user.entity.WalletEntity;
import com.devpaik.payment.adapter.out.persistence.user.repository.UserRepository;
import com.devpaik.payment.adapter.out.persistence.user.repository.WalletRepository;
import com.devpaik.payment.domain.user.Wallet;
import com.devpaik.payment.exception.NotFoundWalletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Import({WalletMapper.class, WalletRepositoryAdapter.class})
class WalletRepositoryAdapterTest {
    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private WalletRepositoryAdapter walletRepositoryAdapter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @BeforeEach
    public void init() {
        UserEntity toEntity = UserEntity.builder()
                .userId("user12345")
                .name("홍길동")
                .createDtm(LocalDateTime.now())
                .build();

        userRepository.save(toEntity);

        WalletEntity walletEntity = WalletEntity.builder()
                .walletId(UUID.randomUUID().toString())
                .user(toEntity)
                .balance(new BigDecimal("1000.45"))
                .currencyCode("USD")
                .build();

        walletRepository.save(walletEntity);

    }

    @DisplayName("회원 지갑 데이터 목록 조회")
    @Test
    public void getMyWalletInfoListTest() {
        List<Wallet> wallets = walletRepositoryAdapter.getMyWalletInfo("user12345");

        assertNotNull(wallets);
        assertEquals(1, wallets.size());
        for (Wallet wallet : wallets) {
            assertEquals("user12345", wallet.getUserId().getId());
            assertEquals("USD", wallet.getCurrencyCode().getValue());
            assertEquals(1000.45, wallet.getBalance().scaleValue(wallet.getCurrencyCode()).doubleValue());
        }
    }

    @DisplayName("회원 지갑 데이터 조회")
    @Test
    public void getMyWalletInfoByCurrencyTest() {
        Wallet wallet = walletRepositoryAdapter.getMyWalletInfoByCurrency("user12345", "USD");

        assertNotNull(wallet);
        assertEquals("user12345", wallet.getUserId().getId());
        assertEquals("USD", wallet.getCurrencyCode().getValue());
        assertEquals(1000.45, wallet.getBalance().scaleValue(wallet.getCurrencyCode()).doubleValue());
    }

    @DisplayName("회원 지갑이 존재하지 않을 경우 오류 처리")
    @Test
    public void errorMyWalletInfoByCurrencyTest() {

        assertThrows(NotFoundWalletException.class, () ->
                walletRepositoryAdapter.getMyWalletInfoByCurrency("user12345", "KRW")
        );

    }
}