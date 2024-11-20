package com.example.odds_receiver.Controller;

import com.example.odds_receiver.Model.Match2;
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

    @PostMapping("/matches")
    public ResponseEntity<String> receiveNormalMatches(@RequestBody List<Map<String, Object>> matches) {
        try {
            // 转换 Map 数据为 Match2 对象
            List<Match2> matchEntities = matches.stream().map(this::mapToMatch).toList();

            // 清空旧数据并插入新数据
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






    // 接收角球比赛信息
    @PostMapping("/corner-matches")
    public ResponseEntity<String> receiveCornerMatches(@RequestBody List<Map<String, Object>> cornerMatches) {
        // 打印接收到的数据
        System.out.println("Received corner matches:");
        cornerMatches.forEach(System.out::println);

        // 可以添加逻辑，例如保存到数据库
        return ResponseEntity.ok("Corner matches received successfully!");
    }
}
