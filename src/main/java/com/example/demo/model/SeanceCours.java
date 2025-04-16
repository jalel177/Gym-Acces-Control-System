package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    @Column(name = "date_seances")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate Date;
    private String Entraineur;
    @JsonIgnore
    @ManyToMany(mappedBy = "seancecours")
    private Set<Commentaire> user =new HashSet<>();

}
