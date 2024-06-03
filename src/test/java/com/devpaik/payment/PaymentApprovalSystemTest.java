package com.devpaik.payment;

import com.devpaik.payment.adapter.in.web.dto.*;
import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.payment.field.PaymentMethod;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentApprovalSystemTest {


    @Autowired
    private TestRestTemplate restTemplate;
    private static final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void init() {
        headers.add("Content-Type", "application/json");
    }


    @Order(1)
    @DisplayName("잔액조회 System Test")
    @Test
    void paymentBalanceSystemTest()  {
        paymentBalance("user1234", "1000.25");
    }

    @Order(2)
    @DisplayName("결제 예상 결과조회 System Test")
    @Test
    void paymentEstimateSystemTest()  {

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("amount", new BigDecimal("150.00"));
        map.put("currency", CurrencyCode.Code.USD);
        map.put("merchantId", "merchantId1234");
        map.put("userId", "user1234");

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(map, headers);

        ResponseEntity<PaymentEstimateResponse> response = restTemplate.exchange(
                "/api/payment/estimate",
                HttpMethod.POST,
                httpEntity, PaymentEstimateResponse.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        PaymentEstimateResponse estimateResponse = response.getBody();
        Assertions.assertThat(estimateResponse).isNotNull();
        Assertions.assertThat(estimateResponse.fees()).isEqualTo(new BigDecimal("4.50"));
        Assertions.assertThat(estimateResponse.estimatedTotal()).isEqualTo(new BigDecimal("154.50"));
        Assertions.assertThat(estimateResponse.currency()).isEqualTo("USD");
    }

    @Order(3)
    @DisplayName("결제 승인 요청 System Test")
    @Test
    void paymentApprovalSystemTest() throws InterruptedException {
        String userId = "user1234";
        String merchantId = "merchantId1234";

        PaymentDetail detail = new PaymentDetail("1234-5678-9123-4567", "12/24" ,"335");
        PaymentApprovalRequest request = new PaymentApprovalRequest(userId, new BigDecimal("150.00"), CurrencyCode.Code.USD,
                merchantId, PaymentMethod.creditCard, detail);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("userId", request.userId());
        map.put("amount", request.amount());
        map.put("currency", request.currency());
        map.put("merchantId", request.merchantId());
        map.put("paymentMethod", request.paymentMethod());
        map.put("paymentDetail", request.paymentDetail());

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(map, headers);

        int nTreadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(nTreadCount);
        for (int i = 0; i < nTreadCount; i ++) {
            executorService.submit(() -> {
                try {
                    ResponseEntity<PaymentApprovalResponse> response = restTemplate.exchange(
                            "/api/payment/approval",
                            HttpMethod.POST,
                            httpEntity, PaymentApprovalResponse.class);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        paymentBalance(userId, "0.00");
    }

    private void paymentBalance(String userId, String actualBalance) {
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<List<WalletView>> response = restTemplate.exchange(
                "/api/payment/balance/" + userId,
                HttpMethod.GET,
                httpEntity, new ParameterizedTypeReference<>() {
                });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<WalletView> walletViews = response.getBody();

        Assertions.assertThat(walletViews).isNotNull();
        Assertions.assertThat(walletViews.size()).isEqualTo(2);
        for (WalletView walletView : walletViews) {
            if("USD".equals(walletView.currency())) {
                Assertions.assertThat(walletView.userId()).isEqualTo("user1234");
                Assertions.assertThat(walletView.currency()).isEqualTo("USD");
                Assertions.assertThat(walletView.balance()).isEqualTo(new BigDecimal(actualBalance));
                break;
            }
        }
    }
}
