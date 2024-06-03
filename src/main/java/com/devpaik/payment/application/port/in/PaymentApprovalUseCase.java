package com.devpaik.payment.application.port.in;

import com.devpaik.payment.application.port.in.command.PaymentApprovalCommand;
import com.devpaik.payment.domain.payment.PaymentApproval;

public interface PaymentApprovalUseCase {
    PaymentApproval paymentApprovalRequest(PaymentApprovalCommand command);
}
