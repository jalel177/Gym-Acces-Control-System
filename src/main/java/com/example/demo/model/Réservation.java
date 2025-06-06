package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name ="réservation")

public class Réservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "réservationid")
    private Long réservid;
    @Enumerated(EnumType.STRING)
    @Column(name = "réservename")
    private Réservation_statue réservname;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "cours_id")
    private SeanceCours seanceCours;
}
