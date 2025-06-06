package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "favoris")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Favoris {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "cours_id", referencedColumnName = "cours_id")
        private SeanceCours seanceCours;
        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "user_id")
        private User user;

        @Column(name = "created_at", updatable = false)
        @CreationTimestamp
        private LocalDateTime createdAt;

        public Favoris(User user, SeanceCours seanceCours) {
            this.user = user;
            this.seanceCours =seanceCours ;
        }
    }

