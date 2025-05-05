package com.devpaik.payment.domain.event;

import java.math.BigDecimal;

public record WithdrawalBalanceEvent(
        String walletId,
        String userId,
        String currencyCode,
        BigDecimal balance
) { }
