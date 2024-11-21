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
@Table(name = "odds", schema = "system")
public class Odd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id", referencedColumnName = "id", nullable = false)
    private Match match; // 关联到 Match

    private String betType;
    private Integer periodNumber;
    private Double hdp;
    private Double points;
    private Double homeOdds;
    private Double awayOdds;
    private Double drawOdds;
    private Double overOdds;
    private Double underOdds;

    @Column(name = "inserted_at", nullable = false)
    private LocalDateTime insertedAt;
}
