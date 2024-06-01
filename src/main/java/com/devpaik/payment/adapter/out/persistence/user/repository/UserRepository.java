package com.devpaik.payment.adapter.out.persistence.user.repository;

import com.devpaik.payment.adapter.out.persistence.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
