package com.example.odds_receiver.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Match2BasicDTO {
    private Long id;
    private String leagueName;
    private String matchTime;
    private String homeTeam;
    private String awayTeam;
    private Integer homeScore;
    private Integer awayScore;
    private LocalDateTime insertedAt;

    public Match2BasicDTO(Long id, String leagueName, String matchTime, String homeTeam, String awayTeam,
                          Integer homeScore, Integer awayScore, LocalDateTime insertedAt) {
        this.id = id;
        this.leagueName = leagueName;
        this.matchTime = matchTime;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.insertedAt = insertedAt;
    }

    // Getters and Setters
}
