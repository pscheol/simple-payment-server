package com.devpaik.user.secondary.jpa.repository;

import com.devpaik.user.secondary.jpa.entity.WalletEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
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
