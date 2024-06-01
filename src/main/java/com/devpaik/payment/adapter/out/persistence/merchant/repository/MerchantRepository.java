package com.devpaik.payment.adapter.out.persistence.merchant.repository;


import com.devpaik.payment.adapter.out.persistence.merchant.entity.MerchantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<MerchantEntity, String> {
}
