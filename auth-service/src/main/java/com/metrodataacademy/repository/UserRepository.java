package com.metrodataacademy.repository;

import com.metrodataacademy.domain.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailOrUsername(String email, String username);

    User findByEmail(String email);

    User findByUsername(String username);
}
