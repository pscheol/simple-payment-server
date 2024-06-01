package com.devpaik.payment.adapter.out.persistence.user.repository;

import com.devpaik.payment.adapter.out.persistence.user.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, String> {

    @Query("""
        SELECT w
          FROM WalletEntity w
         WHERE w.currencyCode = :currencyCode
           AND w.user.userId = :userId
    """)
    Optional<WalletEntity> findByUserCurrencyCode(String userId, String currencyCode);

    @Query("""
        SELECT w
          FROM WalletEntity w
         WHERE w.user.userId = :userId
    """)
    Optional<List<WalletEntity>> findByUser(String userId);
}
