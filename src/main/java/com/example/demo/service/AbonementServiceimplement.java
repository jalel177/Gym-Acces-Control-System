package com.example.demo.service;

import com.example.demo.dao.AbonementRepository;
import com.example.demo.model.Abonement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AbonementServiceimplement implements Abonementinterface {
    @Autowired
    private AbonementRepository abonementrepository;

    @Override
    public Abonement addabo(Abonement abonement) {
        return abonementrepository.save(abonement);
    }


    @Override
    public void deleteabo(Long id) {
        abonementrepository.deleteById(id);
    }

    @Override
    public List<Abonement> addListabonnement(List<Abonement> listabonnement) {
            return abonementrepository.saveAll(listabonnement);
    }

    @Override
    public List<Abonement> getAllabonement() {
        return abonementrepository.findAll();
    }

    @Override
    public Abonement getabonnemnt(Long id) {
        return abonementrepository.findById(id).orElse(null);
    }


    @Override
    public Abonement getabonementsBytype(String ty) {
        return abonementrepository.findAbonementByType(ty);
    }

    @Override
    public List<Abonement> getAllabonementbytype(String ty) {
        return abonementrepository.findAbonementsByType(ty);
    }
    @Override
    public Abonement updateabonnement(Long id, Abonement abonement) {
        abonement = abonementrepository.findById(id).get();
        abonement.setDatedebut(abonement.getDatedebut());
        abonement.setDatefin((abonement.getDatefin()));
        abonement.setType((abonement.getType()));
        return abonementrepository.save(abonement);
    }
    @Override
    public Abonement getabonementsBydatedebut(LocalDate debut) {
        return abonementrepository.findAbonementByDatedebut(debut);
    }

    @Override
    public List<Abonement> getAllabonementbydatedebuts(LocalDate debuts) {
        return abonementrepository.findAbonementsByDatedebuts(debuts);
    }
    @Override
    public Abonement getabonementsBydatefin(LocalDate fin) {
        return abonementrepository.findAbonementByDatefin(fin);
    }

    @Override
    public List<Abonement> getAllabonementbydatefins(LocalDate fins) {
        return abonementrepository.findAbonementsByDatefins(fins);
    }

}