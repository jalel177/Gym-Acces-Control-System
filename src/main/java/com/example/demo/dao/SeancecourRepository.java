package com.example.demo.dao;

import com.example.demo.model.Abonement;
import com.example.demo.model.Entraineur;
import com.example.demo.model.SeanceCours;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface SeancecourRepository extends JpaRepository<SeanceCours,Long> {
    @Query("SELECT s from  SeanceCours s  WHERE s.Entraineur=?1")
    SeanceCours findSeanceCoursByEntraineur(String entraineur);
    @Query("SELECT s from  SeanceCours s  WHERE s.Entraineur=?1")
    List<SeanceCours> findSeanceCoursByEntraineurs(String entraineur);
    @Query("SELECT s from  SeanceCours s  WHERE s.Sportname=?1")
    List<SeanceCours> findSeanceCoursBySportname(String sportname);

    @Query("SELECT s from  SeanceCours s  WHERE s.date=?1")
    SeanceCours findSeanceCoursByDate(String date);
    @Query("SELECT s from  SeanceCours s  WHERE s.date=?1")
    List<SeanceCours> findSeanceCoursByDates(String dates);
    @Modifying
    @Query(value = "DELETE FROM seance_com WHERE cours_id = :coursId", nativeQuery = true)
    @Transactional
    void deleteByCoursId(@Param("coursId") Long coursId);}
