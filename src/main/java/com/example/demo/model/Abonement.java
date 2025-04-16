package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="abonement")
public class Abonement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name= "type")
    private String type;
    @Column(name = "datedebut")
    @NotNull(message = "La date de début d'abonnement est obligatoire")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate Datedebut;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "datefin")
    @NotNull(message = "La date de fin d'abonnement est obligatoire")
    private LocalDate Datefin;






}
