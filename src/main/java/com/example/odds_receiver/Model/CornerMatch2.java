package com.example.odds_receiver.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "corner_matches2", schema = "system")
public class CornerMatch2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String leagueName;    // 联赛名称
    private String matchTime;     // 比赛时间
    private String homeTeam;      // 主队名称
    private String awayTeam;      // 客队名称
    private Integer homeScore;    // 主队得分
    private Integer awayScore;    // 客队得分
    private Integer homeCorners;  // 主队角球数
    private Integer awayCorners;  // 客队角球数

    @ElementCollection
    @CollectionTable(name = "corner_odds2", joinColumns = @JoinColumn(name = "corner_match_id"))
    @MapKeyColumn(name = "odd_key")
    @Column(name = "odd_value")
    private Map<String, String> odds; // 动态存储赔率信息

    private LocalDateTime insertedAt = LocalDateTime.now(); // 数据插入时间
}
