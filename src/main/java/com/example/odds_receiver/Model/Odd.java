package com.example.odds_receiver.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "odds", schema = "system")
public class Odd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "eventId", nullable = false)
    private Match match; // 绑定到Match的eventId

    private String betType;
    private Integer periodNumber;
    private Double hdp;
    private Double points;
    private Double homeOdds;
    private Double awayOdds;
    private Double drawOdds;
    private Double overOdds;
    private Double underOdds;
    private LocalDateTime insertedAt = LocalDateTime.now();

    // Getters and Setters
}
