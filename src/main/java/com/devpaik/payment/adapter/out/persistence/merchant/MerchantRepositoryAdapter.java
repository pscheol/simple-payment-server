package com.devpaik.payment.adapter.out.persistence.merchant;

import com.devpaik.payment.adapter.out.persistence.merchant.repository.MerchantRepository;
import com.devpaik.payment.application.port.out.merchant.GetMerchantPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MerchantRepositoryAdapter implements GetMerchantPort {

    private final MerchantRepository merchantRepository;

    @Override
    public boolean checkMerchantById(String merchantId) {
        return merchantRepository.existsById(merchantId);
    }
}
