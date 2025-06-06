package com.example.demo.service;

import com.example.demo.model.Abonement;
import com.example.demo.model.SeanceCours;

import java.util.List;

public interface Seanceinterface {
    SeanceCours addSeance(SeanceCours seanceCours);

    void deletseance(Long coursid);

    List<SeanceCours> addListseancecours(List<SeanceCours> listseances);

    List<SeanceCours> getAllseances();

    SeanceCours getseance(Long id);

    SeanceCours updateseance(Long coursid, SeanceCours seancecours);

    SeanceCours getseancebyentraineur(String en);

    List<SeanceCours> getAllseancesbyentraineurs(String en);

    List<SeanceCours> getAllseancesbysportsname(String sp);

    SeanceCours getseancebydate(String da);

    List<SeanceCours> getAllseancesbydates(String da);
}
