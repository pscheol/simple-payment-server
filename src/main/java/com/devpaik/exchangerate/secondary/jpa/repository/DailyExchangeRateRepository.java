package com.devpaik.exchangerate.secondary.jpa.repository;

import com.devpaik.exchangerate.secondary.jpa.entity.DailyExchangeRateEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyExchangeRateRepository extends JpaRepository<DailyExchangeRateEntity, Long> {


    @Query("""
           SELECT exr
             FROM DailyExchangeRateEntity exr inner join fetch exr.currency
             WHERE exr.currency.currencyCode = :currencyCode
              AND exr.currentDtm >= :startDtm
    """)
    List<DailyExchangeRateEntity> findByCurrencyCurrencyCodeAndCurrentDtm(String currencyCode, LocalDateTime startDtm, Pageable pageable);
}
