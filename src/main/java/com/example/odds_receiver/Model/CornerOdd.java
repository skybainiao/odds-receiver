package com.example.odds_receiver.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "corner_odds", schema = "system")
public class CornerOdd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "corner_match_id", nullable = false)
    private CornerMatch cornerMatch;

    private String betType;
    private Integer periodNumber;
    private Double hdp;
    private Double points;
    private Double homeOdds;
    private Double awayOdds;
    private Double overOdds;
    private Double underOdds;
    private LocalDateTime insertedAt = LocalDateTime.now();



    // Getters and Setters
}