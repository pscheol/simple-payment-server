package com.devpaik.exchangerate.secondary.jpa.repository;

import com.devpaik.exchangerate.secondary.jpa.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, String> {
}
