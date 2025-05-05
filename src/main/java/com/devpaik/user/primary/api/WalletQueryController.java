package com.devpaik.user.primary.api;

import com.devpaik.payment.primary.api.dto.WalletView;
import com.devpaik.user.application.output.GetUserInfoPort;
import com.devpaik.user.application.output.GetWalletInfoPort;
import com.devpaik.user.domain.Wallet;
import com.devpaik.user.exception.NotFoundUserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "지갑 조회 Query Controller")
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/wallets")
@RestController
public class WalletQueryController {
    private final GetUserInfoPort getUserInfoPort;
    private final GetWalletInfoPort getWalletInfoPort;

    @Operation(summary = "잔액조회")
    @GetMapping("/balance/{userId}")
    public List<WalletView> getBalance(@PathVariable String userId) {
        log.debug("@getBalance userId ={}", userId);


        if (!getUserInfoPort.existUser(userId)) {
            throw new NotFoundUserException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자 입니다.");
        }

        List<Wallet> wallets = getWalletInfoPort.getMyWalletInfo(userId);

        return WalletView.toBuild(wallets);
    }
}
