package com.devpaik.payment.application.service;

import com.devpaik.payment.application.port.in.PaymentApprovalUseCase;
import com.devpaik.payment.application.port.in.command.PaymentApprovalCommand;
import com.devpaik.payment.application.port.in.command.PaymentDetailCommand;
import com.devpaik.payment.application.port.out.exchangerate.GetDailyExchangeRatePort;
import com.devpaik.payment.application.port.out.payment.InsertPaymentApprovalPort;
import com.devpaik.payment.application.port.out.payment.SendCreditCardApprovalPort;
import com.devpaik.payment.application.port.out.user.GetWalletInfoPort;
import com.devpaik.payment.application.port.out.user.UpdateWalletPort;
import com.devpaik.payment.domain.exchangerate.DailyExchangeRate;
import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.exchangerate.field.ExchangeRate;
import com.devpaik.payment.domain.payment.CalculatePayment;
import com.devpaik.payment.domain.payment.PaymentApproval;
import com.devpaik.payment.domain.payment.field.*;
import com.devpaik.payment.domain.user.Wallet;
import com.devpaik.payment.domain.user.field.UserId;
import com.devpaik.payment.exception.InSufficientBalanceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentApprovalService implements PaymentApprovalUseCase {

    private final GetWalletInfoPort getWalletInfoPort;
    private final GetDailyExchangeRatePort getDailyExchangeRatePort;
    private final InsertPaymentApprovalPort insertPaymentApprovalPort;
    private final UpdateWalletPort updateWalletPort;
    private final SendCreditCardApprovalPort sendCreditCardApprovalPort;

    @Value("${payment.fee.default}")
    private String defaultFee;

    @Override
    public PaymentApproval paymentApprovalRequest(PaymentApprovalCommand command) {

        Wallet wallet = getMyWallet(command.userId(), command.currencyCode());
        DailyExchangeRate exchangeRate = getDailyExchangeRate(command.currencyCode());
        CalculatePayment calculatePayment = convertToCalculatePayment(command, wallet, exchangeRate.getExchangeRate());
        log.debug("@@ createPaymentApproval.calculatePayment={}", calculatePayment);

        checkInsufficientBalanceByPayMethodPoint(calculatePayment.paymentMethod(), calculatePayment.cardAmount());

        PaymentApproval paymentApproval = PaymentApproval.createPaymentApproval(calculatePayment);

        if (paymentApproval.getStatus().isRequest()) {
            paymentApproval = sendCreditCardApproval(command.paymentDetail(), paymentApproval);
        }

        savePaymentApproval(paymentApproval);

        log.debug("@@ paymentApprovalRequest.savePaymentApproval={}, calculatePayment.afterWallet()={}", paymentApproval, calculatePayment.afterWallet());

        if (paymentApproval.getStatus().isApproved()) {
            withdrawalBalance(calculatePayment);
        }

        return paymentApproval;
    }

    private PaymentApproval sendCreditCardApproval(PaymentDetailCommand paymentDetail, PaymentApproval paymentApproval) {
        String response = sendCreditCardApprovalPort.sendApproval(paymentDetail.cardNum(), paymentDetail.cvv(), paymentDetail.expiryDate())
                .join();

        log.debug("sendCreditCardApproval response={}", response);
        return "FAIL".equals(response) ?  paymentApproval.sendFailPaymentApproval() : paymentApproval.sendSuccessPaymentApproval();
    }

    private void withdrawalBalance(CalculatePayment calculatePayment) {
        updateWalletPort.withdrawalBalance(calculatePayment.afterWallet());
    }

    private void savePaymentApproval(PaymentApproval paymentApproval) {
        insertPaymentApprovalPort.savePaymentApproval(paymentApproval);
    }

    public PaymentApproval createPaymentApproval(CalculatePayment calculatePayment) {
        return new PaymentApproval(
                calculatePayment.paymentId(),
                calculatePayment.merchantId(),
                calculatePayment.userId(),
                calculatePayment.paymentDtm(),
                calculatePayment.paymentMethod(),
                calculatePayment.cardNum(),
                calculatePayment.status(),
                calculatePayment.amount(),
                calculatePayment.fee(),
                calculatePayment.currencyCode(),
                calculatePayment.exchangeRate(),
                calculatePayment.walletAmount(),
                calculatePayment.cardAmount(),
                calculatePayment.wonAmount(),
                calculatePayment.amountTotal(),
                calculatePayment.afterWallet().parseAfterBalance());
    }

    private CalculatePayment convertToCalculatePayment(PaymentApprovalCommand command, Wallet wallet, ExchangeRate exchangeRate) {
        return CalculatePayment.calculate(command, wallet, exchangeRate, defaultFee);
//        final Amount amount = command.amount();
//        final Fee fee = Fee.createFee(defaultFee).calculate(amount);
//        final AmountTotal amountTotal = new AmountTotal(amount.sum(fee.scaleValue()).getValue());
//
//
//        final WalletAmount walletAmount;
//        final CardAmount cardAmount;
//
//        if (wallet.checkSufficientBalance(amountTotal)) {
//            walletAmount = WalletAmount.create(amountTotal.getValue(), command.currencyCode());
//            cardAmount = CardAmount.createZero();
//        } else {
//            walletAmount = WalletAmount.create(wallet.getBalance().getValue(), command.currencyCode());
//            cardAmount = CardAmount.calculateSubtract(walletAmount, amountTotal, command.currencyCode());
//        }
//
//        return new CalculatePayment(
//                PaymentId.createUUID(),
//                command.merchantId(),
//                command.userId(),
//                PaymentDtm.nowDtm(),
//                command.paymentMethod(),
//                Objects.isNull(command.paymentDetail()) ? null : command.paymentDetail().cardNum(),
//                Status.setStatusAndGet(cardAmount),
//                amount,
//                fee,
//                command.currencyCode(),
//                exchangeRate,
//                walletAmount,
//                cardAmount,
//                WonAmount.calculateWonAmount(amountTotal, exchangeRate),
//                amountTotal,
//                wallet.withdrawWallet(walletAmount)
//        );
    }

    private DailyExchangeRate getDailyExchangeRate(CurrencyCode currencyCode) {
        if (CurrencyCode.KRW.equals(currencyCode.getValue())) {
            return DailyExchangeRate.emptyInstance();
        }
        LocalDateTime startDtm = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return getDailyExchangeRatePort.getLastExchangeRate(currencyCode.getValue(), startDtm);
    }

    private void checkInsufficientBalanceByPayMethodPoint(PaymentMethod paymentMethod, CardAmount cardAmount) {
        if (paymentMethod.isPoint() && cardAmount.isNotZero()) {
            throw new InSufficientBalanceException("잔액이 부족합니다.");
        }
    }

    private Wallet getMyWallet(UserId userId, CurrencyCode currencyCode) {
        return getWalletInfoPort.getMyWalletInfoByCurrency(userId.getId(), currencyCode.getValue());
    }
}
