package com.devpaik.user.secondary.jpa.repository;

import com.devpaik.user.secondary.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
