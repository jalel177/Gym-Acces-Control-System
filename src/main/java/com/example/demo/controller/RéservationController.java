package com.example.demo.controller;

import com.example.demo.DTO.Réservationdto;
import com.example.demo.model.Réservation;
import com.example.demo.service.Réservationinterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("reservation")
public class RéservationController {
    private final Réservationinterface reservationService;

    @Autowired
    public RéservationController(Réservationinterface reservationService) {
        this.reservationService = reservationService;
    }
    @GetMapping("/getreservationbyusername/{username}")
    public ResponseEntity<?> getReservationsByUsername(
            @PathVariable String username
    ) {
        try {
            List<Réservationdto> reservations = reservationService.getReservationsByUsername(username);
            return ResponseEntity.ok(reservations);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body("Error retrieving reservations: " + ex.getMessage());
        }}
    @GetMapping("/getreservationbycoursid/{coursid}")
    public ResponseEntity<?> getReservationsBySession(
            @PathVariable Long coursid
    ) {
        try {
            if (coursid < 1) {
                return ResponseEntity.badRequest().body("Invalid session ID");
            }

            List<Réservationdto> reservations = reservationService.getReservationsBySession(coursid);

            if (reservations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No reservations found for session ID: " + coursid);
            }

            return ResponseEntity.ok(reservations);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());

        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body("Error retrieving reservations: " + ex.getMessage());
        }
    }

    @PostMapping("/addreservation/{userid}/{coursid}")
    public ResponseEntity<Réservation> addReservation(
            @PathVariable String userid,
            @PathVariable Long coursid
    ) {
        Réservation newReservation = reservationService.addReservation(userid, coursid);
        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
    }

    @DeleteMapping("/deletereservation/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();}

    @GetMapping("/getuserreservation/{userid}")
    public ResponseEntity<?> getUserReservations(
            @PathVariable String userid,
            @RequestHeader("Authorization") String token
    ) {
        try {
            // Add token validation logic here if needed
            Map<String, Object> result = reservationService.getUserReservations(userid);

            if ((Boolean) result.get("status")) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", false,
                    "error", "Server error: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/getreservationnumbers/{coursid}")
    public ResponseEntity<?> getParticipantCount(@PathVariable Long coursid) {
        try {
            int count = reservationService.getParticipantCount(coursid);
            return ResponseEntity.ok(Map.of(
                    "coursid", coursid,
                    "participantCount", count
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to get participant count",
                            "details", e.getMessage()
                    ));
        }
    }}