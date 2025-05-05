package com.devpaik.merchant.secondary.jpa;

import com.devpaik.merchant.application.output.GetMerchantPort;
import com.devpaik.merchant.secondary.jpa.repository.MerchantRepository;
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
