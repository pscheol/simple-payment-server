package com.devpaik.payment.adapter.out.persistence.exchangerate.repository;

import com.devpaik.payment.adapter.out.persistence.exchangerate.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, String> {
}
