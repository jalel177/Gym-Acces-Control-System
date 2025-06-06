package com.example.demo.service;

import com.example.demo.DTO.Réservationdto;
import com.example.demo.dao.RéservationRepository;
import com.example.demo.dao.SeancecourRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.Réservation;
import com.example.demo.model.Réservation_statue;
import com.example.demo.model.SeanceCours;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Réservationserviceimplement implements Réservationinterface {
    private final RéservationRepository reservationRepository;

    @Autowired
    public Réservationserviceimplement(RéservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;

    }
    @Autowired
    UserRepository userRepository;
    @Autowired
    SeancecourRepository seancecourRepository;
    @Override
    public List<Réservationdto> getReservationsByUsername(String username) {
        return reservationRepository.findByUsername(username);
    }
    @Override
    public List<Réservationdto> getReservationsBySession(Long coursId) {
        // Basic check in service
        if (!seancecourRepository.existsById(coursId)) {
            throw new IllegalArgumentException("Session not found");
        }

        return reservationRepository.findReservationsBySessionId(coursId);
    }
    @Override
    public Réservation addReservation(String userid,Long  coursid ) {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SeanceCours seanceCours = seancecourRepository.findById(coursid)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        Réservation reservation = new Réservation();
        reservation.setUser(user);
        reservation.setSeanceCours(seanceCours);
        reservation.setRéservname(Réservation_statue.ACTIVE);

        return reservationRepository.save(reservation);}
    @Override
    public int getParticipantCount(Long coursid) {
        return reservationRepository.countUniqueUsersBySessionId(coursid);
    }
    @Override
    public Map<String, Object> getUserReservations(String userid) {
        try {
            List<Réservation> reservations = reservationRepository.findByUserId(userid);

            if (reservations.isEmpty()) {
                return Map.of(
                        "status", false,
                        "error", "No reservations found for this user"
                );
            }

            // Convert reservations to DTOs if needed
            List<Map<String, Object>> reservationData = reservations.stream()
                    .map(reservation -> Map.of(
                            "reservationId", reservation.getRéservid(),
                            "courseId", reservation.getSeanceCours()
                    ))
                    .collect(Collectors.toList());

            return Map.of(
                    "status", true,
                    "reservations", reservationData,
                    "count", reservations.size()
            );

        } catch (Exception e) {
            return Map.of(
                    "status", false,
                    "error", "Error fetching reservations: " + e.getMessage()
            );
        }
    }
    @Override
    public void deleteReservation(Long id) {
        try {
            reservationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Reservation with id " + id + " not found"
            );
        }
    }
}

