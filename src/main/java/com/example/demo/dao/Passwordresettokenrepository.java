package com.example.demo.dao;

import com.example.demo.config.Passwordresettoken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface Passwordresettokenrepository extends JpaRepository<Passwordresettoken,Long> {
    Optional<Passwordresettoken>findByToken(String token);
    void deleteByUserId(String userId);
}

