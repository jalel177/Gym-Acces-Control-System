package com.example.demo.dao;

import com.example.demo.model.Abonement;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface AbonementRepository extends JpaRepository<Abonement,Long> {
    @Query("SELECT a from  Abonement a  WHERE a.type=?1")
    Abonement findAbonementByType (String type);
    @Query("SELECT a from  Abonement a  WHERE a.type=?1")
    List<Abonement> findAbonementsByType (String type);
    @Query("SELECT a from  Abonement a  WHERE a.datedebut=?1")
    List<Abonement> findAbonementsByDatedebuts (LocalDate datedebut);
    @Query("SELECT a from  Abonement a  WHERE a.datedebut=?1")
    Abonement findAbonementByDatedebut (LocalDate datedebut);
    @Query("SELECT a from  Abonement a  WHERE a.datefin=?1")
    List<Abonement> findAbonementsByDatefins (LocalDate datefin);
    @Query("SELECT a from  Abonement a  WHERE a.datefin=?1")
    Abonement findAbonementByDatefin (LocalDate datefin);
    @Query("SELECT a FROM Abonement a WHERE a.user.userid = :userid")
    List<Abonement> findSubscriptionsByUserId(@Param("userid") String userid);
}



