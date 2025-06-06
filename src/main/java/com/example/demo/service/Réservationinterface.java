package com.example.demo.service;

import com.example.demo.DTO.Réservationdto;
import com.example.demo.model.Réservation;

import java.util.List;
import java.util.Map;

public interface Réservationinterface {
    List<Réservationdto> getReservationsByUsername(String username);

    List<Réservationdto> getReservationsBySession(Long coursId);

    Réservation addReservation(String userid , Long coursid );


    int getParticipantCount(Long coursd);

    Map<String, Object> getUserReservations(String userid);

    void deleteReservation(Long id);
}
