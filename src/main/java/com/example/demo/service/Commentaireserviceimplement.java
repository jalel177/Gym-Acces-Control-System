package com.example.demo.service;

import com.example.demo.dao.CommentaireRepository;
import com.example.demo.dao.SeancecourRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.Commentaire;
import com.example.demo.model.SeanceCours;
import com.example.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class Commentaireserviceimplement implements Commentaireinterface{
    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeancecourRepository seanceCoursRepository;

    @Override
    @Transactional
    public Commentaire createCommentaire(Commentaire commentaire, String userid, Long coursid) {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        SeanceCours seanceCours = seanceCoursRepository.findById(coursid)
                .orElseThrow(() -> new RuntimeException("Séance de cours non trouvée"));

        commentaire.setUser(user);
        // instead of commentaire.getSeancecours().add(seanceCours);
        commentaire.setSeancecours(seanceCours);

        // record date+time
        commentaire.setDateCreation(LocalDateTime.now());

        return commentaireRepository.save(commentaire);
    }

    @Override
    public List<Commentaire> getCommentairesBySeanceCours(Long seanceCoursId) {
        return commentaireRepository.findBySeanceCoursId(seanceCoursId);
    }
    @Override
    public void deleteCommentaire(Long commentId) {
        // will throw EmptyResultDataAccessException if not found
        commentaireRepository.deleteById(commentId);
    }

}
