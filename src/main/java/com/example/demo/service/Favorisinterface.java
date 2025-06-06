package com.example.demo.service;

import com.example.demo.model.Favoris;
import jakarta.transaction.Transactional;

import java.util.List;

public interface Favorisinterface {
    @Transactional
    Favoris addToFavoris(String userId, Long coursId);

    @Transactional
    void removeFromFavoris(String userId, Long coursId);

    List<Favoris> getFavorisByUser(String userId);
}
