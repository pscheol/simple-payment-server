package com.devpaik.payment.primary.api;

import com.devpaik.exchangerate.domain.field.CurrencyCode;
import com.devpaik.exchangerate.domain.field.ExchangeRate;
import com.devpaik.merchant.application.output.GetMerchantPort;
import com.devpaik.payment.application.usecase.PaymentApprovalUseCase;
import com.devpaik.payment.application.usecase.command.PaymentApprovalCommand;
import com.devpaik.payment.domain.CalculatePayment;
import com.devpaik.payment.domain.PaymentApproval;
import com.devpaik.payment.domain.field.PaymentMethod;
import com.devpaik.payment.primary.api.dto.PaymentApprovalRequest;
import com.devpaik.payment.primary.api.dto.PaymentDetail;
import com.devpaik.user.application.output.GetUserInfoPort;
import com.devpaik.user.domain.Wallet;
import com.devpaik.user.domain.field.WalletId;
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

        mockMvc.perform(post("/api/payments/approval")
                        .content(objectMapper.writeValueAsString(multiValueMap.toSingleValueMap()))
                        .header("Content-Type", "application/json"))
                .andExpect(status().isCreated());

        verify(getUserInfoPort).existUser(userId);

        verify(getMerchantPort).checkMerchantById(merchantId);

        verify(paymentApprovalUseCase).paymentApprovalRequest(command);
    }

}