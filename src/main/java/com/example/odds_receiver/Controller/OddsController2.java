package com.example.odds_receiver.Controller;

import com.example.odds_receiver.Model.CornerMatch2;
import com.example.odds_receiver.Model.Match2;
import com.example.odds_receiver.Service.CornerMatchService2;
import com.example.odds_receiver.Service.MatchService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/odds")
public class OddsController2 {

    @Autowired
    private MatchService2 matchService2;
    @Autowired
    private CornerMatchService2 cornerMatchService2;

    @PostMapping("/matches")
    public ResponseEntity<String> receiveNormalMatches(@RequestBody List<Map<String, Object>> matches) {
        try {
            List<Match2> matchEntities = matches.stream().map(this::mapToMatch).toList();
            matchService2.replaceAllMatches(matchEntities);
            return ResponseEntity.ok("Matches and odds replaced successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error replacing matches: " + e.getMessage());
        }
    }

    private Match2 mapToMatch(Map<String, Object> matchData) {
        Match2 match = new Match2();
        match.setLeagueName((String) matchData.get("league"));
        match.setMatchTime((String) matchData.get("match_time"));
        match.setHomeTeam((String) matchData.get("home_team"));
        match.setAwayTeam((String) matchData.get("away_team"));
        match.setHomeScore(((Number) matchData.getOrDefault("home_score", 0)).intValue());
        match.setAwayScore(((Number) matchData.getOrDefault("away_score", 0)).intValue());

        // 动态存储赔率字段
        Map<String, String> odds = matchData.entrySet().stream()
                .filter(entry -> !List.of("league", "match_time", "home_team", "away_team", "home_score", "away_score").contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> String.valueOf(entry.getValue())));

        match.setOdds(odds);
        return match;
    }



    @PostMapping("/corner-matches")
    public ResponseEntity<String> receiveCornerMatches(@RequestBody List<Map<String, Object>> cornerMatches) {
        try {
            List<CornerMatch2> cornerMatchEntities = cornerMatches.stream().map(this::mapToCornerMatch).toList();
            cornerMatchService2.replaceAllCornerMatches(cornerMatchEntities);
            return ResponseEntity.ok("Corner matches received and saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving corner matches: " + e.getMessage());
        }
    }

    private CornerMatch2 mapToCornerMatch(Map<String, Object> matchData) {
        CornerMatch2 cornerMatch = new CornerMatch2();
        cornerMatch.setLeagueName((String) matchData.get("league"));
        cornerMatch.setMatchTime((String) matchData.get("match_time"));
        cornerMatch.setHomeTeam((String) matchData.get("home_team"));
        cornerMatch.setAwayTeam((String) matchData.get("away_team"));

        // 使用安全转换方法
        cornerMatch.setHomeScore(parseInteger(matchData.getOrDefault("home_score", 0)));
        cornerMatch.setAwayScore(parseInteger(matchData.getOrDefault("away_score", 0)));


        // 动态存储赔率字段
        Map<String, String> odds = matchData.entrySet().stream()
                .filter(entry -> !List.of("league", "match_time", "home_team", "away_team", "home_score", "away_score").contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> String.valueOf(entry.getValue())));

        cornerMatch.setOdds(odds);
        return cornerMatch;
    }



    private Integer parseInteger(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null; // 或者抛出异常，或者返回默认值
            }
        }
        return null; // 默认值
    }









}
