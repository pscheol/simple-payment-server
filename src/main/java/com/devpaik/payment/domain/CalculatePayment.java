package com.devpaik.payment.domain;

import com.devpaik.exchangerate.domain.field.CurrencyCode;
import com.devpaik.exchangerate.domain.field.ExchangeRate;
import com.devpaik.merchant.domain.field.MerchantId;
import com.devpaik.payment.application.usecase.command.PaymentApprovalCommand;
import com.devpaik.payment.domain.event.WithdrawalBalanceEvent;
import com.devpaik.payment.domain.field.*;
import com.devpaik.payment.exception.InSufficientBalanceException;
import com.devpaik.user.domain.Wallet;
import com.devpaik.user.domain.field.UserId;

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

    public void checkInsufficientBalanceByPayMethodPoint() {
        if (paymentMethod.isPoint() && cardAmount.isNotZero()) {
            throw new InSufficientBalanceException("잔액이 부족합니다.");
        }
    }

    public WithdrawalBalanceEvent withdrawalBalanceEvent() {
        return new WithdrawalBalanceEvent(
                afterWallet.getWalletId().getId(),
                afterWallet.getUserId().getId(),
                afterWallet.getCurrencyCode().getValue(),
                afterWallet.getBalance().getValue()
        );
    }
}
