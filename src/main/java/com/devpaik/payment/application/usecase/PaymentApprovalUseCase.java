package com.devpaik.payment.application.usecase;

import com.devpaik.payment.application.usecase.command.PaymentApprovalCommand;
import com.devpaik.payment.domain.PaymentApproval;

public interface PaymentApprovalUseCase {
    PaymentApproval paymentApprovalRequest(PaymentApprovalCommand command);
}
