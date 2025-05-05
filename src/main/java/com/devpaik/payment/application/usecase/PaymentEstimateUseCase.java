package com.devpaik.payment.application.usecase;

import com.devpaik.payment.application.usecase.command.PaymentEstimateCommand;
import com.devpaik.payment.domain.PaymentEstimate;

public interface PaymentEstimateUseCase {

    PaymentEstimate calculatePaymentEstimate(PaymentEstimateCommand command);
}
