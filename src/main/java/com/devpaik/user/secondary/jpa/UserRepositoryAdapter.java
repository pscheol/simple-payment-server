package com.devpaik.user.secondary.jpa;

import com.devpaik.user.application.output.GetUserInfoPort;
import com.devpaik.user.secondary.jpa.repository.UserRepository;
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
