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
@Table(name = "matches2", schema = "system")
public class Match2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String leagueName;    // 联赛名称
    private String matchTime;     // 比赛时间
    private String homeTeam;      // 主队名称
    private String awayTeam;      // 客队名称
    private Integer homeScore;    // 主队得分
    private Integer awayScore;    // 客队得分

    @ElementCollection
    @CollectionTable(name = "odds2", joinColumns = @JoinColumn(name = "match_id"))
    @MapKeyColumn(name = "odd_key")
    @Column(name = "odd_value")
    private Map<String, String> odds;  // 动态存储赔率信息

    private LocalDateTime insertedAt = LocalDateTime.now();  // 数据插入时间
}
