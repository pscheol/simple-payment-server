package com.devpaik.payment.domain.field;

public enum PaymentMethod {
    creditCard, point;


    public boolean isPoint() {
        return point.equals(this);
    }

    public boolean isCreditCard() {
        return creditCard.equals(this);
    }
}
