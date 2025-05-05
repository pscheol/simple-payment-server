package com.devpaik.payment.secondary.jpa;

import com.devpaik.exchangerate.domain.field.CurrencyCode;
import com.devpaik.exchangerate.domain.field.ExchangeRate;
import com.devpaik.payment.application.usecase.command.PaymentApprovalCommand;
import com.devpaik.payment.domain.CalculatePayment;
import com.devpaik.payment.domain.PaymentApproval;
import com.devpaik.payment.domain.field.PaymentMethod;
import com.devpaik.payment.primary.api.dto.PaymentApprovalRequest;
import com.devpaik.payment.primary.api.dto.PaymentDetail;
import com.devpaik.user.domain.Wallet;
import com.devpaik.user.domain.field.WalletId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.ZoneId;

@DataJpaTest
@Import({PaymentApprovalMapper.class, PaymentApprovalAdapter.class})
class PaymentApprovalAdapterTest {

    @Autowired
    private PaymentApprovalMapper paymentApprovalMapper;

    @Autowired
    private PaymentApprovalAdapter paymentApprovalAdapter;

    @BeforeEach
    public void init() {

    }
    @DisplayName("결제승인정보 저장")
    @Test
    void paymentApprovalSaveTest() {
        String defaultFee = "0.03";

        ExchangeRate exchangeRate = new ExchangeRate(new BigDecimal("1319.51"));
        Wallet wallet = Wallet.createWallet(WalletId.genWalletId(), "user1234", "USD", new BigDecimal("10000.50"));
        PaymentDetail detail = new PaymentDetail("1234-5678-9123-4567", "12/24" ,"335");

        PaymentApprovalRequest request = new PaymentApprovalRequest("userId", new BigDecimal("150.00"), CurrencyCode.Code.USD,
                "merchantId1234", PaymentMethod.creditCard, detail);
        PaymentApprovalCommand command =PaymentApprovalCommand.of(request, ZoneId.systemDefault());

        CalculatePayment calculatePayment = CalculatePayment.calculate(command, wallet, exchangeRate, defaultFee);
        PaymentApproval paymentApproval = PaymentApproval.createPaymentApproval(calculatePayment);

        paymentApprovalAdapter.savePaymentApproval(paymentApproval);
    }
}