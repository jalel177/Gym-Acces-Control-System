package com.example.demo.controller;

import com.example.demo.model.Commentaire;
import com.example.demo.service.Commentaireinterface;
import com.example.demo.service.Commentaireserviceimplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("commentaires")
public class CommentaireController {
    @Autowired
    private Commentaireinterface commentaireinterface;

    @PostMapping("addcom/{userid}/{seanceCoursId}")
    public ResponseEntity<Commentaire> createCommentaire(
            @RequestBody Commentaire commentaire,
            @PathVariable String userid,
            @PathVariable Long seanceCoursId // Paramètre renommé
    ) {
        Commentaire nouveauCommentaire = commentaireinterface.createCommentaire(
                commentaire,
                userid,
                seanceCoursId
        );
        return new ResponseEntity<>(nouveauCommentaire, HttpStatus.CREATED);
    }

    // Endpoint modifié
    @GetMapping("/seance/{seanceCoursId}")
    public ResponseEntity<List<Commentaire>> getCommentairesBySeance(
            @PathVariable Long seanceCoursId
    ) {
        return ResponseEntity.ok(
                commentaireinterface.getCommentairesBySeanceCours(seanceCoursId)
        );
    }
}

