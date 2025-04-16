package com.example.demo.service;

import com.example.demo.model.Commentaire;
import jakarta.transaction.Transactional;

import java.util.List;

public interface Commentaireinterface {
    @Transactional
    Commentaire createCommentaire(Commentaire commentaire, String utilisateurId, Long seanceCoursId);

    List<Commentaire> getCommentairesBySeanceCours(Long seanceCoursId);
}
