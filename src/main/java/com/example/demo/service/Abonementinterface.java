package com.example.demo.service;

import com.example.demo.model.Abonement;
import com.example.demo.model.User;

import java.time.LocalDate;
import java.util.List;

public interface Abonementinterface {
    Abonement addabo(Abonement abonement);
    void deleteabo(Long id);

    List<Abonement> addListabonnement(List<Abonement> listabonnement);

    List<Abonement> getAllabonement();

    Abonement getabonnemnt(Long id);

    Abonement getabonementsBytype(String ty);

    List<Abonement> getAllabonementbytype(String ty);

    Abonement updateabonnement(Long id, Abonement abonement);

    Abonement getabonementsBydatedebut(LocalDate debut);

    List<Abonement> getAllabonementbydatedebuts(LocalDate debuts);

    Abonement getabonementsBydatefin(LocalDate fin);

    List<Abonement> getAllabonementbydatefins(LocalDate fins);
}
