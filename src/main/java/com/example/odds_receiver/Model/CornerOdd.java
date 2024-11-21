package com.example.odds_receiver.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "corner_odds", schema = "system")
public class CornerOdd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "corner_match_id", referencedColumnName = "id", nullable = false)
    private CornerMatch cornerMatch; // 关联到 CornerMatch

    private String betType;
    private Integer periodNumber;
    private Double hdp;
    private Double points;
    private Double homeOdds;
    private Double awayOdds;
    private Double overOdds;
    private Double underOdds;

    @Column(name = "inserted_at", nullable = false)
    private LocalDateTime insertedAt;
}
