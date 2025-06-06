package com.example.demo.service;

import com.example.demo.model.Commentaire;
import jakarta.transaction.Transactional;

import java.util.List;

public interface Commentaireinterface {
    @Transactional
    Commentaire createCommentaire(Commentaire commentaire, String userid, Long coursid);

    List<Commentaire> getCommentairesBySeanceCours(Long seanceCoursId);

    void deleteCommentaire(Long commentId);
}
