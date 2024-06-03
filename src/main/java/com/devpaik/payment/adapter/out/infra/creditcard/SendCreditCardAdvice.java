package com.devpaik.payment.adapter.out.infra.creditcard;

import com.devpaik.payment.application.port.out.payment.SendCreditCardApprovalPort;
import com.devpaik.payment.domain.payment.field.CardNum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class SendCreditCardAdvice implements SendCreditCardApprovalPort {

    @Async("sendCreditCardExecutor")
    @Override
    public CompletableFuture<String> sendApproval(CardNum cardNum, String cvv, String expiryDate) {
        log.debug("@@ sendApproval cardNum={}, cvv={}, expiryDate={}", cardNum, cvv, expiryDate);

        final Random random = new Random();
        return CompletableFuture.completedFuture(random.nextInt(2) == 1 ? "SUCCESS" : "FAIL");
    }
}
