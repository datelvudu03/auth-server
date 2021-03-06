package com.authserver.repository;

import com.authserver.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    AppUser findByUsername(String username);

    List<AppUser> findByUsernameAndPassword(String username, String password);

    AppUser existsByUsername(String username);
    boolean existsByUsernameAndPassword(String username,String password);
}
