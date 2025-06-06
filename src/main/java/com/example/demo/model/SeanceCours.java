package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "seancecours")
public class SeanceCours {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "cours_id")
    private Long coursid;
    private String Sportname;
    @Column(name = "date_seances")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Example: "2023-10-05 14:30:00"
    private LocalDateTime date;
    private String Entraineur;
    private String duration;
    @JsonIgnore
    @OneToMany(
            mappedBy = "seancecours",
            cascade = CascadeType.ALL, // Cascade all operations
            orphanRemoval = true)
    private Set<Commentaire> commentaires =new HashSet<>();
    @ManyToMany(mappedBy = "seancecours")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "seanceCours", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Réservation> reservations = new HashSet<>();
    @OneToMany(mappedBy = "seanceCours", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Favoris> favoris = new HashSet<>();
    @PreRemove
    private void preRemove() {
        users.clear(); // Break relationship before deletion
    }
}
