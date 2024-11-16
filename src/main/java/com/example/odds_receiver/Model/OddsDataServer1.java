package com.example.odds_receiver.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OddsDataServer1 {



    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LeagueData {
        private String league_name;
        private List<EventData> events = new ArrayList<>(); // 初始化为空列表
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EventData {
        private Long event_id;
        private String home_team;
        private String away_team;
        private String start_time;
        private Integer home_score;
        private Integer away_score;
        private List<OddsInfo> odds;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OddsInfo {
        private String betType;
        private Integer periodNumber;
        private Double hdp;
        private Double homeOdds;
        private Double drawOdds;
        private Double awayOdds;
        private Double overOdds;
        private Double underOdds;
        private Double points;
    }
}
