package com.example.demo.service;

import com.example.demo.model.Abonement;
import com.example.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Abonementinterface {

    @Transactional
    Abonement addAbo(@RequestBody Map<String, Object> payload);

    void deleteabo(Long id);

    List<Abonement> addListabonnement(List<Abonement> listabonnement);

    List<Abonement> getAllabonement();

    Abonement getabonnemnt(Long id);

    Abonement getabonementsBytype(String ty);


   List <Abonement> getSubscriptionsByUserId(String userid);

    List<Abonement> getAllabonementbytype(String ty);

    Abonement updateabonnement(Long id, Abonement abonement);

    Abonement getabonementsBydatedebut(LocalDate debut);

    List<Abonement> getAllabonementbydatedebuts(LocalDate debuts);

    Abonement getabonementsBydatefin(LocalDate fin);

    List<Abonement> getAllabonementbydatefins(LocalDate fins);
}
