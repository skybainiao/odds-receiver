// src/main/java/com/example/odds_receiver/Model/Binding.java

package com.example.odds_receiver.Model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bindings", schema = "system")
public class Binding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "league1_name", nullable = false)
    private String league1Name;

    @Column(name = "league2_name", nullable = false)
    private String league2Name;

    @Column(name = "home_team1_name", nullable = false)
    private String homeTeam1Name;

    @Column(name = "home_team2_name", nullable = false)
    private String homeTeam2Name;

    @Column(name = "away_team1_name", nullable = false)
    private String awayTeam1Name;

    @Column(name = "away_team2_name", nullable = false)
    private String awayTeam2Name;
}
