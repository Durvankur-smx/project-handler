package com.druv.projecttool.repository;

import com.druv.projecttool.entity.User;
import com.druv.projecttool.entity.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndStatus(Long id, UserStatus status);
}
