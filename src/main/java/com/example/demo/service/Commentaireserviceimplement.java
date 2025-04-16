package com.example.demo.service;

import com.example.demo.dao.CommentaireRepository;
import com.example.demo.dao.MemberRepository;
import com.example.demo.dao.SeancecourRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.Commentaire;
import com.example.demo.model.Member;
import com.example.demo.model.SeanceCours;
import com.example.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public Commentaire createCommentaire(Commentaire commentaire, String utilisateurId, Long seanceCoursId) {
        User user = userRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        SeanceCours seanceCours = seanceCoursRepository.findById(seanceCoursId)
                .orElseThrow(() -> new RuntimeException("Séance de cours non trouvée"));

        commentaire.setUser(user);
        commentaire.getSeancecours().add(seanceCours); // Ajoutez à la collection existante
        commentaire.setDateCreation(LocalDate.now());

        return commentaireRepository.save(commentaire);
    }
    @Override
    public List<Commentaire> getCommentairesBySeanceCours(Long seanceCoursId) {
        return commentaireRepository.findBySeanceCoursId(seanceCoursId);
    }

}
