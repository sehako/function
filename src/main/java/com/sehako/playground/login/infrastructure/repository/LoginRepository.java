package com.sehako.playground.login.infrastructure.repository;

import com.sehako.playground.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByNickname(String nickname);
}
