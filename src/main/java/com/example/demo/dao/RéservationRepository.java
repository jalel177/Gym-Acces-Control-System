package com.example.demo.dao;

import com.example.demo.DTO.Réservationdto;
import com.example.demo.model.Réservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RéservationRepository extends JpaRepository<Réservation, Long> {
    // In RéservationRepository.java
// In RéservationRepository.java
    @Query(
            "SELECT new com.example.demo.DTO.Réservationdto(" +
                    "   r.réservid,     " +  // Long
                    "   u.userid,       " +  // String
                    "   u.username,     " +  // String
                    "   r.réservname    " +  // Réservation_statue enum
                    ") " +
                    "FROM Réservation r " +
                    " JOIN r.user u " +
                    "WHERE r.seanceCours.coursid = :coursId"
    )
    List<Réservationdto> findReservationsBySessionId(Long coursId);


    @Query("SELECT new com.example.demo.DTO.Réservationdto(r.réservid, u.username, r.réservname) " +
            "FROM Réservation r " +
            "JOIN r.user u " +
            "WHERE u.username = :username")
    List<Réservationdto> findByUsername(@Param("username") String username);

    // Find all reservations for a user
    @Query("SELECT r FROM Réservation r WHERE r.user.userid = :userId")
    List<Réservation> findByUserId(@Param("userId") String userId);

    // Check if any reservations exist for a session
    boolean existsBySeanceCoursCoursid(Long coursId);

    // Custom delete by user and session

        @Query("SELECT COUNT(DISTINCT r.user.userid) FROM Réservation r WHERE r.seanceCours.coursid = :coursid")
        int countUniqueUsersBySessionId(@Param("coursid") Long coursid);
    }


