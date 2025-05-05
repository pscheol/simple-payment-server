package com.devpaik.payment.secondary.jpa;

import com.devpaik.payment.domain.PaymentApproval;
import com.devpaik.payment.secondary.jpa.entity.PaymentApprovalEntity;
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
