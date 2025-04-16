package com.example.demo.dao;

import com.example.demo.model.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CommentaireRepository extends JpaRepository<Commentaire,Long> {
    @Query("SELECT c FROM Commentaire c JOIN c.seancecours s WHERE s.coursid = :coursId")
    List<Commentaire> findBySeanceCoursId(@Param("coursId") Long coursId);

}
