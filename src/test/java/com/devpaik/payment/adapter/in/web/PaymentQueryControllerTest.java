package com.devpaik.payment.adapter.in.web;

import com.devpaik.payment.adapter.in.web.dto.PaymentEstimateRequest;
import com.devpaik.payment.application.port.in.PaymentEstimateUseCase;
import com.devpaik.payment.application.port.in.command.PaymentEstimateCommand;
import com.devpaik.payment.application.port.out.merchant.GetMerchantPort;
import com.devpaik.payment.application.port.out.user.GetUserInfoPort;
import com.devpaik.payment.application.port.out.user.GetWalletInfoPort;
import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.payment.PaymentEstimate;
import com.devpaik.payment.domain.payment.field.Amount;
import com.devpaik.payment.domain.payment.field.Fee;
import com.devpaik.payment.domain.user.Wallet;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PaymentQueryController.class)
class PaymentQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private  GetWalletInfoPort getWalletInfoPort;

    @MockBean
    private  GetUserInfoPort getUserInfoPort;

    @MockBean
    private  GetMerchantPort getMerchantPort;

    @MockBean
    private  PaymentEstimateUseCase paymentEstimateUseCase;

    private ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("잔액조회 Test")
    @Test
    void getBalanceTest() throws Exception {
        String userId = "user1234";
        boolean returnRecipe = true;

        List<Wallet> wallets = new ArrayList<>();
        wallets.add(Wallet.createWallet("wallet1234", userId, "USD", new BigDecimal("150.00")));
        when(getUserInfoPort.existUser(userId)).thenReturn(returnRecipe);


        when(getWalletInfoPort.getMyWalletInfo(userId)).thenReturn(wallets);

        mockMvc.perform(get("/api/payment/balance/" + userId)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isOk());

        verify(getUserInfoPort).existUser(userId);

        verify(getWalletInfoPort).getMyWalletInfo(userId);
    }

    @DisplayName("결제 예상 결과조회 Test")
    @Test
    void paymentEstimateTest() throws Exception {
        String userId = "user1234";
        String merchantId = "merchantId1234";
        PaymentEstimateRequest request = new PaymentEstimateRequest(
                new BigDecimal("150.00"),
                CurrencyCode.Code.USD,
                "merchantId1234",
                "userId1234");

        PaymentEstimateCommand command = PaymentEstimateCommand.of(request);

        Amount amount = new Amount(request.amount());
        Fee fees = Fee.createFee("4.50").calculate(amount);
        PaymentEstimate paymentEstimate = new PaymentEstimate(amount.sum(fees.scaleValue()), fees, command.currencyCode());

        when(getMerchantPort.checkMerchantById(merchantId)).thenReturn(true);
        when(paymentEstimateUseCase.calculatePaymentEstimate(command)).thenReturn(paymentEstimate);



        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("amount", new BigDecimal("150.00"));
        multiValueMap.add("currency", CurrencyCode.Code.USD);
        multiValueMap.add("merchantId", merchantId);
        multiValueMap.add("userId", userId);

        mockMvc.perform(post("/api/payment/estimate")
                        .content(objectMapper.writeValueAsString(multiValueMap.toSingleValueMap()))
                        .header("Content-Type", "application/json"))
                .andExpect(status().isOk());



        verify(getMerchantPort).checkMerchantById(merchantId);
        verify(paymentEstimateUseCase).calculatePaymentEstimate(command);
    }
}