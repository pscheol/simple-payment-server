package com.devpaik.payment.application.usecase.service;

import com.devpaik.payment.application.usecase.PaymentEstimateUseCase;
import com.devpaik.payment.application.usecase.command.PaymentEstimateCommand;
import com.devpaik.payment.domain.PaymentEstimate;
import com.devpaik.payment.domain.field.Amount;
import com.devpaik.payment.domain.field.Fee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentService implements PaymentEstimateUseCase {

    @Value("${payment.fee.default}")
    private String defaultFee;

    @Override
    public PaymentEstimate calculatePaymentEstimate(PaymentEstimateCommand command) {
        Fee fee = Fee.createFee(defaultFee);

        Fee fees = fee.calculate(command.amount());

        Amount estimateTotal = command.amount().sum(fees.getValue());

        log.debug("@@ calculator estimate fees={}, estimateTotal={}", fees, estimateTotal);

        return new PaymentEstimate(estimateTotal, fees, command.currencyCode());
    }
}
