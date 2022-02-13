package com.codex.machina.ex.homini.repository;

import com.codex.machina.ex.homini.entity.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long>
{
    PasswordToken findByToken(String token);
}
