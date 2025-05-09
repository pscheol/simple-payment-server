package com.devpaik.payment.primary.api;

import com.devpaik.merchant.application.output.GetMerchantPort;
import com.devpaik.payment.application.usecase.PaymentApprovalUseCase;
import com.devpaik.payment.application.usecase.command.PaymentApprovalCommand;
import com.devpaik.payment.domain.PaymentApproval;
import com.devpaik.payment.exception.NotFoundMerchantInfoException;
import com.devpaik.payment.primary.api.dto.PaymentApprovalRequest;
import com.devpaik.payment.primary.api.dto.PaymentApprovalResponse;
import com.devpaik.user.application.output.GetUserInfoPort;
import com.devpaik.user.exception.NotFoundUserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Calendar;

@Tag(name = "결제 승인 Controller")
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentApprovalController {
    private final GetUserInfoPort getUserInfoPort;
    private final GetMerchantPort getMerchantPort;
    private final PaymentApprovalUseCase paymentApprovalUseCase;


    @Operation(summary = "결제 승인 요청")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/approval")
    public PaymentApprovalResponse paymentApproval(@Valid @RequestBody PaymentApprovalRequest approvalRequest, HttpServletRequest request) {
        log.debug("@@ paymentApproval approvalRequest={}", approvalRequest);

        if (!getUserInfoPort.existUser(approvalRequest.userId())) {
            throw new NotFoundUserException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자 입니다.");
        }

        if (!getMerchantPort.checkMerchantById(approvalRequest.merchantId())) {
            throw new NotFoundMerchantInfoException(HttpStatus.BAD_REQUEST, "존재하지 않는 상점 정보입니다.");
        }

        PaymentApprovalCommand command = PaymentApprovalCommand.of(approvalRequest, getZoneId(request));
        PaymentApproval approval = paymentApprovalUseCase.paymentApprovalRequest(command);

        return PaymentApprovalResponse.toBuild(approval);
    }

    private ZoneId getZoneId(HttpServletRequest request) {
        return Calendar.getInstance(request.getLocale()).getTimeZone().toZoneId();
    }
}
