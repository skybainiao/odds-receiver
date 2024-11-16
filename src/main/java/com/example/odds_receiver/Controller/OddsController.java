package com.example.odds_receiver.Controller;

import com.example.odds_receiver.Model.OddsDataServer1;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@RestController
public class OddsController {

    private static final Logger logger = LoggerFactory.getLogger(OddsController.class);

    private final ObjectMapper objectMapper;

    @Autowired
    public OddsController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping("/receive_odds_server1")
    public ResponseEntity<?> receiveOddsFromServer1(@RequestBody List<Map<String, Object>> data) {
        if (data == null || data.isEmpty()) {
            logger.warn("No valid data received");
            return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "No valid data received"));
        }
        System.out.println(data);
        try {

            for (Map<String, Object> match : data) {

                String league = (String) match.get("league");
                String matchTime = (String) match.get("match_time");
                String homeTeam = (String) match.get("home_team");
                String awayTeam = (String) match.get("away_team");
                String homeScore = String.valueOf(match.get("home_score"));
                String awayScore = String.valueOf(match.get("away_score"));

                //logger.info("League: {}, Match: {} vs {}, Time: {}, Score: {} - {}",
                        //league, homeTeam, awayTeam, matchTime, homeScore, awayScore);

                // 动态处理所有赔率字段
                //for (Map.Entry<String, Object> entry : match.entrySet()) {
                    //String key = entry.getKey();
                    //if (key.startsWith("MONEYLINE") || key.startsWith("SPREAD") || key.startsWith("TOTAL_POINTS")) {
                        //logger.info("OddsType: {}, Values: {}", key, entry.getValue());
                    //}
                //}
            }

            return ResponseEntity.ok(Map.of("status", "success"));

        } catch (Exception e) {
            logger.error("Error processing data", e);
            return ResponseEntity.status(500).body(Map.of("status", "fail", "message", "Internal server error"));
        }
    }






}
