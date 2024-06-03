package com.devpaik.payment.domain.payment;

import com.devpaik.payment.application.port.in.command.PaymentApprovalCommand;
import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.exchangerate.field.ExchangeRate;
import com.devpaik.payment.domain.merchant.field.MerchantId;
import com.devpaik.payment.domain.payment.field.*;
import com.devpaik.payment.domain.user.Wallet;
import com.devpaik.payment.domain.user.field.UserId;

import java.util.Objects;

public record CalculatePayment(
        PaymentId paymentId,
        MerchantId merchantId,
        UserId userId,
        PaymentDtm paymentDtm,
        PaymentMethod paymentMethod,
        CardNum cardNum,
        Status status,
        Amount amount,
        Fee fee,
        CurrencyCode currencyCode,
        ExchangeRate exchangeRate,
        WalletAmount walletAmount,
        CardAmount cardAmount,
        WonAmount wonAmount,
        AmountTotal amountTotal,
        Wallet afterWallet
) {

    public static CalculatePayment calculate(PaymentApprovalCommand command, Wallet wallet, ExchangeRate exchangeRate, String defaultFee) {
        final Amount amount = command.amount();
        final Fee fee = Fee.createFee(defaultFee).calculate(amount);
        final AmountTotal amountTotal = new AmountTotal(amount.sum(fee.scaleValue()).getValue());


        final WalletAmount walletAmount;
        final CardAmount cardAmount;

        if (wallet.checkSufficientBalance(amountTotal)) {
            walletAmount = WalletAmount.create(amountTotal.getValue(), command.currencyCode());
            cardAmount = CardAmount.createZero();
        } else {
            walletAmount = WalletAmount.create(wallet.getBalance().getValue(), command.currencyCode());
            cardAmount = CardAmount.calculateSubtract(walletAmount, amountTotal, command.currencyCode());
        }

        return new CalculatePayment(
                PaymentId.createUUID(),
                command.merchantId(),
                command.userId(),
                PaymentDtm.nowDtm(command.zoneId()),
                command.paymentMethod(),
                Objects.isNull(command.paymentDetail()) ? null : command.paymentDetail().cardNum(),
                Status.setStatusAndGet(cardAmount),
                amount,
                fee,
                command.currencyCode(),
                exchangeRate,
                walletAmount,
                cardAmount,
                WonAmount.calculateWonAmount(amountTotal, exchangeRate),
                amountTotal,
                wallet.withdrawWallet(walletAmount)
        );
    }

}
