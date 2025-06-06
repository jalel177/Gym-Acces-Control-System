package com.example.demo.dao;

import com.example.demo.model.Favoris;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavorisRepository extends JpaRepository<Favoris ,Long> {
    boolean existsByUser_UseridAndSeanceCours_Coursid(String userid, Long coursid);

    List<Favoris> findByUser_Userid(String userid);

    void deleteByUser_UseridAndSeanceCours_Coursid(String userid, Long coursid);
}

