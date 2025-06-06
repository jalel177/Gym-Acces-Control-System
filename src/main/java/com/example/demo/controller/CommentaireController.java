package com.example.demo.controller;

import com.example.demo.DTO.CreateCommentdto;
import com.example.demo.model.Commentaire;
import com.example.demo.service.Commentaireinterface;
import com.example.demo.service.Commentaireserviceimplement;
import jakarta.validation.Valid;
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

    @PostMapping("/addcom/{userid}/{coursid}")
    public ResponseEntity<Commentaire> createCommentaire(
            @PathVariable String userid,
            @PathVariable Long coursid,
            @Valid @RequestBody CreateCommentdto dto
    ) {
        // build the entity here (Option 1 from before)
        Commentaire commentaire = new Commentaire();
        commentaire.setContenu(dto.getContenu());

        Commentaire nouveau = commentaireinterface.createCommentaire(
                commentaire,
                userid,
                coursid
        );
        return new ResponseEntity<>(nouveau, HttpStatus.CREATED);
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
    @DeleteMapping("/deletecom/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentaireinterface.deleteCommentaire(id);
        return ResponseEntity.noContent().build();
    }
}

