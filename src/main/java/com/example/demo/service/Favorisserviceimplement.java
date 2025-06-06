package com.example.demo.service;

import com.example.demo.dao.FavorisRepository;
import com.example.demo.dao.SeancecourRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.Favoris;
import com.example.demo.model.SeanceCours;
import com.example.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Favorisserviceimplement implements Favorisinterface{
    @Autowired
    private FavorisRepository favorisRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeancecourRepository seancecourRepository;

    @Override
    @Transactional
    public Favoris addToFavoris(String userid, Long coursid) {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SeanceCours session = seancecourRepository.findById(coursid)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (favorisRepository.existsByUser_UseridAndSeanceCours_Coursid(userid, coursid)) {
            throw new RuntimeException("Session already in favorites");
        }

        Favoris favoris = new Favoris(user, session);
        return favorisRepository.save(favoris);
    }

    @Override
    @Transactional
    public void removeFromFavoris(String userid, Long coursid) {
        favorisRepository.deleteByUser_UseridAndSeanceCours_Coursid(userid, coursid);
    }

    @Override
    public List<Favoris> getFavorisByUser(String userid) {
        return favorisRepository.findByUser_Userid(userid);
    }
}


