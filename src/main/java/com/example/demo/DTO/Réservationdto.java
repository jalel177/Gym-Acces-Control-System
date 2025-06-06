package com.example.demo.DTO;

import com.example.demo.model.Réservation_statue;

public class Réservationdto {
    private Long reservationId;
    private String userId;
    private String username;
    private String status;

    /**
     * Constructor for queries returning (reservationId, userId, username, statusEnum)
     */
    public Réservationdto(
            Long reservationId,
            String userId,
            String username,
            Réservation_statue statusEnum
    ) {
        this.reservationId = reservationId;
        this.userId        = userId;
        this.username      = username;
        this.status        = statusEnum.name();
    }

    /**
     * Constructor for queries returning (reservationId, username, statusEnum)
     * userId will be left null when using this constructor.
     */
    public Réservationdto(
            Long reservationId,
            String username,
            Réservation_statue statusEnum
    ) {
        this.reservationId = reservationId;
        this.userId        = null;
        this.username      = username;
        this.status        = statusEnum.name();
    }

    // Getters
    public Long   getReservationId() { return reservationId; }
    public String getUserId()        { return userId;        }
    public String getUsername()      { return username;      }
    public String getStatus()        { return status;        }
}
