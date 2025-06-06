package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name="user_id")
@Table(name="entraineur")
public class Entraineur extends User {
    @Column(name = "specialite") // Nom de colonne simplifié
    private String specialite;




}



