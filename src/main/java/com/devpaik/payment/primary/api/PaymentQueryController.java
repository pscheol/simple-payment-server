package com.devpaik.payment.primary.api;

import com.devpaik.merchant.application.output.GetMerchantPort;
import com.devpaik.payment.application.usecase.PaymentEstimateUseCase;
import com.devpaik.payment.application.usecase.command.PaymentEstimateCommand;
import com.devpaik.payment.domain.PaymentEstimate;
import com.devpaik.payment.exception.NotFoundMerchantInfoException;
import com.devpaik.payment.primary.api.dto.PaymentEstimateRequest;
import com.devpaik.payment.primary.api.dto.PaymentEstimateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "결제 조회 Query Controller")
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/payments")
public class PaymentQueryController {

    private final GetMerchantPort getMerchantPort;
    private final PaymentEstimateUseCase paymentEstimateUseCase;

    @Operation(summary = "결제 예상 결과조회")
    @PostMapping("/estimate")
    public PaymentEstimateResponse paymentEstimate(@Valid @RequestBody PaymentEstimateRequest request) {
        log.debug("@paymentEstimate request ={}", request);

        if (!getMerchantPort.checkMerchantById(request.merchantId())) {
            throw new NotFoundMerchantInfoException(HttpStatus.BAD_REQUEST, "존재하지 않는 상점 정보입니다.");
        }

        PaymentEstimateCommand command = PaymentEstimateCommand.of(request);

        PaymentEstimate paymentEstimate = paymentEstimateUseCase.calculatePaymentEstimate(command);

        return PaymentEstimateResponse.toBuild(paymentEstimate);
    }
}
