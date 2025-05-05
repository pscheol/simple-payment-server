package com.devpaik.payment.application.usecase.service;

import com.devpaik.exchangerate.domain.DailyExchangeRate;
import com.devpaik.exchangerate.domain.field.CurrencyCode;
import com.devpaik.payment.application.output.InsertPaymentApprovalPort;
import com.devpaik.payment.application.output.SendCreditCardApprovalPort;
import com.devpaik.payment.application.provider.ExchangeRateProvider;
import com.devpaik.payment.application.provider.WalletProvider;
import com.devpaik.payment.application.usecase.command.PaymentApprovalCommand;
import com.devpaik.payment.domain.CalculatePayment;
import com.devpaik.payment.domain.PaymentApproval;
import com.devpaik.payment.domain.field.PaymentMethod;
import com.devpaik.payment.domain.field.WalletAmount;
import com.devpaik.payment.primary.api.dto.PaymentApprovalRequest;
import com.devpaik.payment.primary.api.dto.PaymentDetail;
import com.devpaik.user.domain.Wallet;
import com.devpaik.user.domain.field.UserId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@ExtendWith(SpringExtension.class)
class PaymentApprovalServiceTest {
    private final ApplicationEventPublisher eventPublisher = Mockito.mock(ApplicationEventPublisher.class);
    private final ExchangeRateProvider exchangeRateProvider = Mockito.mock(ExchangeRateProvider.class);
    private final WalletProvider walletProvider = Mockito.mock(WalletProvider.class);
    private final InsertPaymentApprovalPort insertPaymentApprovalPort = Mockito.mock(InsertPaymentApprovalPort.class);
    private final SendCreditCardApprovalPort sendCreditCardApprovalPort = Mockito.mock(SendCreditCardApprovalPort.class);


    private final PaymentApprovalService paymentService = new PaymentApprovalService(
            eventPublisher, exchangeRateProvider, walletProvider,
            insertPaymentApprovalPort, sendCreditCardApprovalPort);

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(paymentService, "defaultFee", "0.03");

    }

    @DisplayName("결제 승인요청")
    @Test
    public void paymentApprovalRequestTest() throws Exception {
        PaymentDetail detail = new PaymentDetail("1234-5678-9123-4567", "12/24" ,"335");
        PaymentApprovalRequest request = new PaymentApprovalRequest("userId1234", new BigDecimal("100.00"), CurrencyCode.Code.USD,
                "merchantId1234", PaymentMethod.creditCard, detail);
        PaymentApprovalCommand command = PaymentApprovalCommand.of(request, ZoneId.systemDefault());


        LocalDateTime startDtm = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);

        DailyExchangeRate exchangeRate = DailyExchangeRate.createDailyExchangeRate(1L, "USD",
                new BigDecimal("1482.52"), startDtm);
        BDDMockito.given(exchangeRateProvider.getDailyExchangeRate(command.currencyCode().getValue()))
                .willReturn(exchangeRate);

        Wallet wallet = Wallet.createWallet("wallet1", "userId1234", "USD", new BigDecimal("1000.00"));
        BDDMockito.given(walletProvider.getMyWallet(new UserId("userId1234"), new CurrencyCode(CurrencyCode.USD)))
                .willReturn(wallet);


        CalculatePayment calculatePayment = CalculatePayment.calculate(command, wallet, exchangeRate.getExchangeRate(), "0.03");
        PaymentApproval paymentApproval = PaymentApproval.createPaymentApproval(calculatePayment);
        BDDMockito.given(insertPaymentApprovalPort.savePaymentApproval(paymentApproval))
                .willReturn(paymentApproval);



        Wallet afterWallet = wallet.withdrawWallet(WalletAmount.create(calculatePayment.walletAmount().getValue(), calculatePayment.currencyCode()));

        PaymentApproval approval = paymentService.paymentApprovalRequest(command);
        Assertions.assertEquals(approval.getAfterBalance().getValue(), afterWallet.getBalance().getValue());
    }
}