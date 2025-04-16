package com.example.demo.service;

import com.example.demo.dao.SeancecourRepository;
import com.example.demo.model.SeanceCours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SeanceServiceimplement implements  Seanceinterface {
    @Autowired
    private SeancecourRepository seancecourrepo;

    @Override
    public SeanceCours addSeance(SeanceCours seanceCours) {
        return seancecourrepo.save(seanceCours);
    }

    @Override
    public void deletseance(Long id) {
        seancecourrepo.deleteById(id);
    }

    @Override
    public List<SeanceCours> addListseancecours(List<SeanceCours> listseances) {
        return seancecourrepo.saveAll(listseances);
    }
    @Override
    public List<SeanceCours> getAllseances() {
        return seancecourrepo.findAll();
    }

    @Override
    public SeanceCours getseance(Long id) {
        return seancecourrepo.findById(id).orElse(null);
    }
    @Override
    public SeanceCours updateseance(Long id, SeanceCours seancecours) {
        seancecours = seancecourrepo.findById(id).get();
        seancecours.setDate(seancecours.getDate());
        seancecours.setEntraineur((seancecours.getEntraineur()));

        return seancecourrepo.save(seancecours);
    }
    @Override
    public SeanceCours getseancebyentraineur(String en) {
        return seancecourrepo.findSeanceCoursByEntraineur(en);
    }

    @Override
    public List<SeanceCours> getAllseancesbyentraineurs(String en) {
        return seancecourrepo.findSeanceCoursByEntraineurs(en);
    }
    @Override
    public SeanceCours getseancebydate(String da) {
        return seancecourrepo.findSeanceCoursByDate(da);
    }

    @Override
    public List<SeanceCours> getAllseancesbydates(String da) {
        return seancecourrepo.findSeanceCoursByDates(da);
    }

}