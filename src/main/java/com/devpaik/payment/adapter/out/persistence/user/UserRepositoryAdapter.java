package com.devpaik.payment.adapter.out.persistence.user;

import com.devpaik.payment.adapter.out.persistence.user.repository.UserRepository;
import com.devpaik.payment.application.port.out.user.GetUserInfoPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserRepositoryAdapter implements GetUserInfoPort {

    private final UserRepository userRepository;

    @Override
    public boolean existUser(String userId) {
        return userRepository.existsById(userId);
    }
}
