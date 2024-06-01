package com.devpaik.payment.adapter.out.persistence.payment.repository;

import com.devpaik.payment.adapter.out.persistence.payment.entity.PaymentApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentApprovalRepository extends JpaRepository<PaymentApprovalEntity, String> {
}
