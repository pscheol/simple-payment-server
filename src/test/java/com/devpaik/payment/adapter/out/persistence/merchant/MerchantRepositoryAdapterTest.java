package com.devpaik.payment.adapter.out.persistence.merchant;

import com.devpaik.payment.adapter.out.persistence.merchant.entity.MerchantEntity;
import com.devpaik.payment.adapter.out.persistence.merchant.repository.MerchantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@Import({MerchantRepositoryAdapter.class})
class MerchantRepositoryAdapterTest {

    @Autowired
    private MerchantRepositoryAdapter merchantRepositoryAdapter;

    @Autowired
    private MerchantRepository merchantRepository;

    @BeforeEach
    public void init() {
        merchantRepository.save(MerchantEntity.builder()
                        .merchantId("merchantId1234")
                        .merchantName("테스트상점")
                .build())  ;
    }

    @DisplayName("상점조회 여부 조회")
    @Test
    public void existMerchantTest() {
        assertTrue(merchantRepositoryAdapter.checkMerchantById("merchantId1234"));
    }
}