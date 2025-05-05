package com.devpaik.payment.secondary.jpa;

import com.devpaik.payment.application.output.InsertPaymentApprovalPort;
import com.devpaik.payment.domain.PaymentApproval;
import com.devpaik.payment.secondary.jpa.entity.PaymentApprovalEntity;
import com.devpaik.payment.secondary.jpa.repository.PaymentApprovalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PaymentApprovalAdapter implements InsertPaymentApprovalPort {

    private final PaymentApprovalRepository paymentApprovalRepository;
    private final PaymentApprovalMapper paymentApprovalMapper;

    @Override
    public PaymentApproval savePaymentApproval(PaymentApproval paymentApproval) {
        PaymentApprovalEntity entity = paymentApprovalMapper.mapToPaymentApprovalEntity(paymentApproval);
        return paymentApprovalMapper.mapToDomain(paymentApprovalRepository.save(entity));

    }
}
