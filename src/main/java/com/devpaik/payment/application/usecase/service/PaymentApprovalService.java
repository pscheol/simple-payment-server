package com.devpaik.payment.application.usecase.service;

import com.devpaik.exchangerate.domain.DailyExchangeRate;
import com.devpaik.payment.application.output.InsertPaymentApprovalPort;
import com.devpaik.payment.application.output.SendCreditCardApprovalPort;
import com.devpaik.payment.application.provider.ExchangeRateProvider;
import com.devpaik.payment.application.provider.WalletProvider;
import com.devpaik.payment.application.usecase.PaymentApprovalUseCase;
import com.devpaik.payment.application.usecase.command.PaymentApprovalCommand;
import com.devpaik.payment.application.usecase.command.PaymentDetailCommand;
import com.devpaik.payment.domain.CalculatePayment;
import com.devpaik.payment.domain.PaymentApproval;
import com.devpaik.user.domain.Wallet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentApprovalService implements PaymentApprovalUseCase {
    private final ApplicationEventPublisher eventPublisher;
    private final ExchangeRateProvider exchangeRateProvider;
    private final WalletProvider walletProvider;
    private final InsertPaymentApprovalPort insertPaymentApprovalPort;
    private final SendCreditCardApprovalPort sendCreditCardApprovalPort;

    @Value("${payment.fee.default}")
    private String defaultFee;

    @Override
    public PaymentApproval paymentApprovalRequest(PaymentApprovalCommand command) {

        Wallet wallet = walletProvider.getMyWallet(command.userId(), command.currencyCode());
        DailyExchangeRate exchangeRate = exchangeRateProvider.getDailyExchangeRate(command.currencyCode().getValue());
        CalculatePayment calculatePayment = CalculatePayment.calculate(command, wallet, exchangeRate.getExchangeRate(), defaultFee);

        log.debug("@@ createPaymentApproval.calculatePayment={}", calculatePayment);

        calculatePayment.checkInsufficientBalanceByPayMethodPoint();

        PaymentApproval paymentApproval = PaymentApproval.createPaymentApproval(calculatePayment);

        if (paymentApproval.getStatus().isRequest()) {
            paymentApproval = sendCreditCardApproval(command.paymentDetail(), paymentApproval);
        }

        insertPaymentApprovalPort.savePaymentApproval(paymentApproval);

        log.debug("@@ paymentApprovalRequest.savePaymentApproval={}, calculatePayment.afterWallet()={}", paymentApproval, calculatePayment.afterWallet());


        if (paymentApproval.getStatus().isApproved()) {
            eventPublisher.publishEvent(calculatePayment.withdrawalBalanceEvent());
        }

        return paymentApproval;
    }

    private PaymentApproval sendCreditCardApproval(PaymentDetailCommand paymentDetail, PaymentApproval paymentApproval) {
        String response = sendCreditCardApprovalPort.sendApproval(paymentDetail.cardNum(), paymentDetail.cvv(), paymentDetail.expiryDate())
                .join();

        log.debug("sendCreditCardApproval response={}", response);
        return "FAIL".equals(response) ?  paymentApproval.sendFailPaymentApproval() : paymentApproval.sendSuccessPaymentApproval();
    }
}
