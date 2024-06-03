package com.devpaik.payment.adapter.out.persistence.payment;

import com.devpaik.payment.adapter.out.persistence.payment.entity.PaymentApprovalEntity;
import com.devpaik.payment.adapter.out.persistence.payment.repository.PaymentApprovalRepository;
import com.devpaik.payment.application.port.out.payment.InsertPaymentApprovalPort;
import com.devpaik.payment.domain.payment.PaymentApproval;
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
