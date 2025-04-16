package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name="user_id")
@Table(name="member")
public class Member extends User {
    @Column(name = "type_abonement") // Nom de colonne simplifié
    private String type_abonement;





}
