package com.devpaik.payment.adapter.in.web;

import com.devpaik.payment.adapter.in.web.dto.PaymentEstimateRequest;
import com.devpaik.payment.adapter.in.web.dto.PaymentEstimateResponse;
import com.devpaik.payment.adapter.in.web.dto.WalletView;
import com.devpaik.payment.application.port.in.PaymentEstimateUseCase;
import com.devpaik.payment.application.port.in.command.PaymentEstimateCommand;
import com.devpaik.payment.application.port.out.merchant.GetMerchantPort;
import com.devpaik.payment.application.port.out.user.GetUserInfoPort;
import com.devpaik.payment.application.port.out.user.GetWalletInfoPort;
import com.devpaik.payment.domain.payment.PaymentEstimate;
import com.devpaik.payment.domain.user.Wallet;
import com.devpaik.payment.exception.NotFoundMerchantInfoException;
import com.devpaik.payment.exception.NotFoundtUserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "결제 조회 Query Controller")
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/payment")
public class PaymentQueryController {

    private final GetWalletInfoPort getWalletInfoPort;
    private final GetUserInfoPort getUserInfoPort;
    private final GetMerchantPort getMerchantPort;

    private final PaymentEstimateUseCase paymentEstimateUseCase;

    @Operation(summary = "잔액조회")
    @GetMapping("/balance/{userId}")
    public List<WalletView> getBalance(@PathVariable String userId) {
        log.debug("@getBalance userId ={}", userId);


        if (!getUserInfoPort.existUser(userId)) {
            throw new NotFoundtUserException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자 입니다.");
        }

        List<Wallet> wallets = getWalletInfoPort.getMyWalletInfo(userId);

        return WalletView.toBuild(wallets);
    }

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
