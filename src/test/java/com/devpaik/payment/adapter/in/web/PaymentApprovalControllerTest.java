package com.devpaik.payment.adapter.in.web;

import com.devpaik.payment.adapter.in.web.dto.PaymentApprovalRequest;
import com.devpaik.payment.adapter.in.web.dto.PaymentDetail;
import com.devpaik.payment.application.port.in.PaymentApprovalUseCase;
import com.devpaik.payment.application.port.in.command.PaymentApprovalCommand;
import com.devpaik.payment.application.port.out.merchant.GetMerchantPort;
import com.devpaik.payment.application.port.out.user.GetUserInfoPort;
import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.exchangerate.field.ExchangeRate;
import com.devpaik.payment.domain.payment.CalculatePayment;
import com.devpaik.payment.domain.payment.PaymentApproval;
import com.devpaik.payment.domain.payment.field.PaymentMethod;
import com.devpaik.payment.domain.user.Wallet;
import com.devpaik.payment.domain.user.field.WalletId;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.ZoneId;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PaymentApprovalController.class)
class PaymentApprovalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetMerchantPort getMerchantPort;

    @MockBean
    private GetUserInfoPort getUserInfoPort;

    @MockBean
    private PaymentApprovalUseCase paymentApprovalUseCase;

    private ObjectMapper objectMapper = new ObjectMapper();


    @DisplayName("결제 승인 요청 Test")
    @Test
    void paymentApprovalTest() throws Exception {
        String userId = "user1234";
        String merchantId = "merchantId1234";

        PaymentDetail detail = new PaymentDetail("1234-5678-9123-4567", "12/24" ,"335");
        PaymentApprovalRequest request = new PaymentApprovalRequest(userId, new BigDecimal("150.00"), CurrencyCode.Code.USD,
                merchantId, PaymentMethod.creditCard, detail);
        PaymentApprovalCommand command = PaymentApprovalCommand.of(request, ZoneId.systemDefault());

        ExchangeRate exchangeRate = new ExchangeRate(new BigDecimal("1319.51"));
        Wallet wallet = Wallet.createWallet(WalletId.genWalletId(), "user1234", "USD", new BigDecimal("10000.50"));
        CalculatePayment calculatePayment = CalculatePayment.calculate(command, wallet, exchangeRate, "0.03");
        PaymentApproval paymentApproval = PaymentApproval.createPaymentApproval(calculatePayment);

        when(getUserInfoPort.existUser(userId)).thenReturn(true);
        when(getMerchantPort.checkMerchantById(merchantId)).thenReturn(true);
        when(paymentApprovalUseCase.paymentApprovalRequest(command)).thenReturn(paymentApproval);


        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("userId", request.userId());
        multiValueMap.add("amount", request.amount());
        multiValueMap.add("currency", request.currency());
        multiValueMap.add("merchantId", request.merchantId());
        multiValueMap.add("paymentMethod", request.paymentMethod());
        multiValueMap.add("paymentDetail", request.paymentDetail());

        mockMvc.perform(post("/api/payment/approval")
                        .content(objectMapper.writeValueAsString(multiValueMap.toSingleValueMap()))
                        .header("Content-Type", "application/json"))
                .andExpect(status().isOk());

        verify(getUserInfoPort).existUser(userId);

        verify(getMerchantPort).checkMerchantById(merchantId);

        verify(paymentApprovalUseCase).paymentApprovalRequest(command);
    }

}