package com.devpaik.payment.secondary.jpa.repository;

import com.devpaik.payment.secondary.jpa.entity.PaymentApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentApprovalRepository extends JpaRepository<PaymentApprovalEntity, String> {
}
