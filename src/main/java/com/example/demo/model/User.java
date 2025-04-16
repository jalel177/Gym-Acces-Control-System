package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Getter
@Setter
@Data
@Table(name="users")
public class User {
    @Id
    @Column(name = "user_id")
    private String userid;
    @Column(name="firstName" , length = 10,nullable = true)
    @Size(max=10, message =" le prénom ne doit pas depasser 10 caractéres")
    private String firstName;
    private String lastName;
    @Column(length=100,nullable = false,unique = true)
    private String email;
    private  String username;
    private  String address ;
    private String password ;
    private String confPassword;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Commentaire> comments;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private Set<Role> roles =new HashSet<>();}






