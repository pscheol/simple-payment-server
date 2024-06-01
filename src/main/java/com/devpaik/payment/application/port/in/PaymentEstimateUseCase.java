package com.devpaik.payment.application.port.in;

import com.devpaik.payment.application.port.in.command.PaymentEstimateCommand;
import com.devpaik.payment.domain.payment.PaymentEstimate;

public interface PaymentEstimateUseCase {

    PaymentEstimate calculatePaymentEstimate(PaymentEstimateCommand command);
}
