package com.devpaik.payment.application.service;

import com.devpaik.payment.adapter.in.web.dto.PaymentApprovalRequest;
import com.devpaik.payment.adapter.in.web.dto.PaymentDetail;
import com.devpaik.payment.application.port.in.command.PaymentApprovalCommand;
import com.devpaik.payment.application.port.out.exchangerate.GetDailyExchangeRatePort;
import com.devpaik.payment.application.port.out.payment.InsertPaymentApprovalPort;
import com.devpaik.payment.application.port.out.payment.SendCreditCardApprovalPort;
import com.devpaik.payment.application.port.out.user.GetWalletInfoPort;
import com.devpaik.payment.application.port.out.user.UpdateWalletPort;
import com.devpaik.payment.domain.exchangerate.DailyExchangeRate;
import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.payment.CalculatePayment;
import com.devpaik.payment.domain.payment.PaymentApproval;
import com.devpaik.payment.domain.payment.field.PaymentMethod;
import com.devpaik.payment.domain.payment.field.WalletAmount;
import com.devpaik.payment.domain.user.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ExtendWith(SpringExtension.class)
class PaymentApprovalServiceTest {
    private final GetWalletInfoPort getWalletInfoPort = Mockito.mock(GetWalletInfoPort.class);
    private final GetDailyExchangeRatePort getDailyExchangeRatePort = Mockito.mock(GetDailyExchangeRatePort.class);
    private final InsertPaymentApprovalPort insertPaymentApprovalPort = Mockito.mock(InsertPaymentApprovalPort.class);
    private final UpdateWalletPort updateWalletPort = Mockito.mock(UpdateWalletPort.class);
    private final SendCreditCardApprovalPort sendCreditCardApprovalPort = Mockito.mock(SendCreditCardApprovalPort.class);

    private final PaymentApprovalService paymentService = new PaymentApprovalService(
            getWalletInfoPort, getDailyExchangeRatePort, insertPaymentApprovalPort,
            updateWalletPort, sendCreditCardApprovalPort);

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
        PaymentApprovalCommand command = PaymentApprovalCommand.of(request);


        LocalDateTime startDtm = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);

        DailyExchangeRate exchangeRate = DailyExchangeRate.createDailyExchangeRate(1L, "USD",
                new BigDecimal("1482.52"), startDtm);
        BDDMockito.given(getDailyExchangeRatePort.getLastExchangeRate(command.currencyCode().getValue(), startDtm))
                .willReturn(exchangeRate);

        Wallet wallet = Wallet.createWallet("wallet1", "userId1234", "USD", new BigDecimal("1000.00"));
        BDDMockito.given(getWalletInfoPort.getMyWalletInfoByCurrency("userId1234", CurrencyCode.USD))
                .willReturn(wallet);


        CalculatePayment calculatePayment = CalculatePayment.calculate(command, wallet, exchangeRate.getExchangeRate(), "0.03");
        PaymentApproval paymentApproval = PaymentApproval.createPaymentApproval(calculatePayment);
        BDDMockito.given(insertPaymentApprovalPort.savePaymentApproval(paymentApproval))
                .willReturn(paymentApproval);



        Wallet afterWallet = wallet.withdrawWallet(WalletAmount.create(calculatePayment.walletAmount().getValue(), calculatePayment.currencyCode()));
        BDDMockito.when(updateWalletPort.withdrawalBalance(afterWallet)).thenReturn(afterWallet);

        PaymentApproval approval = paymentService.paymentApprovalRequest(command);
        Assertions.assertEquals(approval.getAfterBalance().getValue(), afterWallet.getBalance().getValue());
    }
}