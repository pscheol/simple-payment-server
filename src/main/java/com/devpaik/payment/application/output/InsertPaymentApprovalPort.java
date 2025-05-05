package com.devpaik.payment.application.output;

import com.devpaik.payment.domain.PaymentApproval;

public interface InsertPaymentApprovalPort {
    PaymentApproval savePaymentApproval(PaymentApproval paymentApproval);
}
