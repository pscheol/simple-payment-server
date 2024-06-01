package com.devpaik.payment.application.service;

import com.devpaik.payment.adapter.in.web.dto.PaymentEstimateRequest;
import com.devpaik.payment.application.port.in.command.PaymentEstimateCommand;
import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.payment.PaymentEstimate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PaymentServiceTest {


    private final PaymentService paymentService = new PaymentService();


    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(paymentService, "defaultFee", "0.03");
    }
    @DisplayName("USD 예상결제금액 조회")
    @Test
    public void paymentEstimateByUSDTest() {
        PaymentEstimateRequest request = new PaymentEstimateRequest(
                new BigDecimal("150.00"),
                CurrencyCode.Code.USD,
                "merchantId1234",
                "userId12345");
        PaymentEstimateCommand command = PaymentEstimateCommand.of(request);
        PaymentEstimate estimate = paymentService.calculatePaymentEstimate(command);

        CurrencyCode currencyCode = estimate.currency();
        assertEquals(new BigDecimal("154.50"), estimate.parseEstimateTotal());
        assertEquals(new BigDecimal("4.50"), estimate.parseFee());
        assertEquals("USD", estimate.parseCurrencyCode());
    }

    @DisplayName("KRW 예상결제금액 조회")
    @Test
    public void paymentEstimateKRWTest() {
        PaymentEstimateRequest request = new PaymentEstimateRequest(
                new BigDecimal("150"),
                CurrencyCode.Code.KRW,
                "merchantId1234",
                "userId12345");

        PaymentEstimateCommand command = PaymentEstimateCommand.of(request);
        PaymentEstimate estimate = paymentService.calculatePaymentEstimate(command);

        CurrencyCode currencyCode = estimate.currency();
        assertEquals(new BigDecimal("154"), estimate.parseEstimateTotal());
        assertEquals(new BigDecimal("4.50"), estimate.parseFee());
        assertEquals("KRW", estimate.parseCurrencyCode());
    }
}