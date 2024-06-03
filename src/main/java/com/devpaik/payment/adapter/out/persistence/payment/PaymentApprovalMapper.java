package com.devpaik.payment.adapter.out.persistence.payment;

import com.devpaik.payment.adapter.out.persistence.payment.entity.PaymentApprovalEntity;
import com.devpaik.payment.domain.payment.PaymentApproval;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PaymentApprovalMapper {
    public PaymentApprovalEntity mapToPaymentApprovalEntity(PaymentApproval paymentApproval) {
        return PaymentApprovalEntity.builder()
                .paymentId(paymentApproval.getPaymentId().getId())
                .merchantId(paymentApproval.getMerchantId().getId())
                .userId(paymentApproval.getUserId().getId())
                .paymentDtm(paymentApproval.getPaymentDtm().getDatetime())
                .paymentMethod(paymentApproval.getPaymentMethod())
                .cardNum(paymentApproval.getCardNum().getValue())
                .status(paymentApproval.getStatus())
                .amount(paymentApproval.getAmount().getValue())
                .fee(paymentApproval.getFee().getValue())
                .currencyCode(paymentApproval.getCurrencyCode().getValue())
                .exchangeRate(Objects.isNull(paymentApproval.getExchangeRate()) ? null : paymentApproval.getExchangeRate().getValue())
                .walletAmount(paymentApproval.getWalletAmount().getValue())
                .cardAmount(paymentApproval.getCardAmount().getValue())
                .wonAmount(paymentApproval.getWonAmount().getValue())
                .amountTotal(paymentApproval.getAmountTotal().getValue())
                .afterBalance(paymentApproval.getAfterBalance().getValue())
                .build();
    }

    public PaymentApproval mapToDomain(PaymentApprovalEntity entity) {
        return PaymentApproval.createPaymentApprovalByEntity(entity);
    }
}
