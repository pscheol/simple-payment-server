package com.devpaik.user.secondary.jpa;

import com.devpaik.user.secondary.jpa.entity.UserEntity;
import com.devpaik.user.secondary.jpa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import({UserRepositoryAdapter.class})
class UserRepositoryAdapterTest {

    @Autowired
    private UserRepositoryAdapter userRepositoryAdapter;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void init() {
        UserEntity toEntity = UserEntity.builder()
                .userId("user1234")
                .name("홍길동")
                .createDtm(LocalDateTime.now())
                .build();

        userRepository.save(toEntity);
    }

    @DisplayName("회원 존재여부 조회")
    @Test
    public void existUserTest() {
        assertTrue(userRepositoryAdapter.existUser("user1234"));
    }
}