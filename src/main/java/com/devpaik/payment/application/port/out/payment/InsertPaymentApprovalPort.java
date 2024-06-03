package com.devpaik.payment.application.port.out.payment;

import com.devpaik.payment.domain.payment.PaymentApproval;

public interface InsertPaymentApprovalPort {
    PaymentApproval savePaymentApproval(PaymentApproval paymentApproval);
}
