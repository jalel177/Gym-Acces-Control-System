package com.example.demo.service;

import com.example.demo.dao.AbonementRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.Abonement;
import com.example.demo.model.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class AbonementServiceimplement implements Abonementinterface {
    @Autowired
    private AbonementRepository abonementrepository;
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AbonementServiceimplement.class);
    @Override
    @Transactional
    public Abonement addAbo(@RequestBody Map<String, Object> payload) {
        logger.info("Received payload: {}", payload); // Add logging

        String userId = payload.get("userid").toString();
        logger.info("Extracted userid: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found - ID: " + userId));

        String type = (String) payload.get("type");
        String startDate = (String) payload.get("datedebut");
        String endDate = (String) payload.get("datefin");

        logger.info("Creating subscription - Type: {}, Start: {}, End: {}", type, startDate, endDate);

        Abonement abonement = new Abonement();
        abonement.setType(type);
        abonement.setDatedebut(LocalDate.parse(startDate));
        abonement.setDatefin(LocalDate.parse(endDate));
        abonement.setUser(user);

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
    public List<Abonement> getSubscriptionsByUserId(String userid) {
        return abonementrepository.findSubscriptionsByUserId(userid);
    }


@Override
    public List<Abonement> getAllabonementbytype(String ty) {
        return abonementrepository.findAbonementsByType(ty);
    }
    @Override
    public Abonement updateabonnement(Long id, Abonement abonement) {
        Abonement existing = abonementrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        // 2. Update fields directly from the incoming object
        existing.setType(abonement.getType());
        existing.setDatedebut(abonement.getDatedebut());
        existing.setDatefin(abonement.getDatefin());

        // 3. Save updated entity
        return abonementrepository.save(existing);
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