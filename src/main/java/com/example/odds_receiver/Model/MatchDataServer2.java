package com.example.odds_receiver.Model;

import lombok.Data;
import java.util.Map;

@Data
public class MatchDataServer2 {
    private String league;
    private String match_time;
    private String home_team;
    private String away_team;
    private String home_score;
    private String away_score;
    private Map<String, String> odds;
}
