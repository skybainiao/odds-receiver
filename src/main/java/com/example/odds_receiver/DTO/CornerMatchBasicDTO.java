package com.example.odds_receiver.DTO;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CornerMatchBasicDTO {
    private Long id;
    private Long eventId;
    private String leagueName;
    private LocalDateTime matchTime;
    private String homeTeam;
    private String awayTeam;
    private Integer homeScore;
    private Integer awayScore;

    // 构造方法
    public CornerMatchBasicDTO(Long id, Long eventId, String leagueName, LocalDateTime matchTime,
                               String homeTeam, String awayTeam, Integer homeScore, Integer awayScore) {
        this.id = id;
        this.eventId = eventId;
        this.leagueName = leagueName;
        this.matchTime = matchTime;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    // Getters 和 Setters
}
