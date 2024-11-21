package com.example.odds_receiver.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matches", schema = "system") // 移除 uniqueConstraints
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    private String leagueName;
    private LocalDateTime matchTime;
    private String homeTeam;
    private String awayTeam;
    private Integer homeScore;
    private Integer awayScore;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Odd> odds = new ArrayList<>(); // 初始化列表
}
