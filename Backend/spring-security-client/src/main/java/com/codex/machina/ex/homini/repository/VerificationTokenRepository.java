package com.codex.machina.ex.homini.repository;

import com.codex.machina.ex.homini.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>
{
    VerificationToken findByToken(String token);
}
