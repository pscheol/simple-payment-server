package com.devpaik.merchant.secondary.jpa.repository;


import com.devpaik.merchant.secondary.jpa.entity.MerchantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<MerchantEntity, String> {
}
