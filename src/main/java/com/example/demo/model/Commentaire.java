package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name ="comentaires")
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="com_id")
    private Long id;
    @Lob // Pour les longs textes
    @Column(nullable = false)
    @NotBlank(message = "Le contenu ne peut pas être vide")
    private String contenu;
    @Column(name = "date_creation")
    private LocalDate dateCreation;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    @ManyToMany
    @JoinTable(name = "seance_com",joinColumns = @JoinColumn(name = "com_id", referencedColumnName = "com_id"),
            inverseJoinColumns = @JoinColumn(name ="cours_id", referencedColumnName = "cours_id"))
    private Set<SeanceCours> seancecours =new HashSet<>();
}
