package com.authserver.repository;

import com.authserver.entity.AppUser;
import com.authserver.entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtTokenRepository extends JpaRepository<JwtToken,Long> {
}
