package com.codex.machina.ex.homini.repository;

import com.codex.machina.ex.homini.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>
{
    User findByEmail(String email);
    User findByUsername(String username);
    java.util.List<User> findAll();
}
