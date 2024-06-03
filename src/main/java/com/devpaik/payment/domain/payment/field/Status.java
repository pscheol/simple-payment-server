package com.devpaik.payment.domain.payment.field;

import java.math.BigDecimal;

public enum Status {
    request, approved, failed;

    public static Status setStatusAndGet(CardAmount cardAmount) {
        if (cardAmount.getValue().equals(BigDecimal.ZERO)) {
            return approved;
        } else {
            return request;
        }
    }

    public boolean isRequest() {
        return request.equals(this);
    }
    public boolean isApproved() {
        return approved.equals(this);
    }
}
