package com.example.odds_receiver.Controller;

import com.example.odds_receiver.Model.Match;
import com.example.odds_receiver.Repository.MatchRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OddsController {

    @Autowired
    private MatchRepository matchRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/receive_odds_server1")
    @Transactional
    public ResponseEntity<?> receiveOdds(@RequestBody List<Match> matches) {
        if (matches == null || matches.isEmpty()) {
            return ResponseEntity.badRequest().body("No valid data received");
        }

        matches.forEach(match -> {
            match.getOdds().forEach(odd -> odd.setMatch(match));
            matchRepository.save(match);
        });

        // 插入日志
        Query insertLogQuery = entityManager.createNativeQuery("INSERT INTO system.store_logs (operation_time) VALUES (CURRENT_TIMESTAMP)");
        insertLogQuery.executeUpdate();

        // 检查日志记录是否超过12条
        Query countLogsQuery = entityManager.createNativeQuery("SELECT COUNT(*) FROM system.store_logs");
        long logCount = ((Number) countLogsQuery.getSingleResult()).longValue();

        if (logCount > 12) {
            matchRepository.deleteMatchesByOldestLog();
            matchRepository.deleteOldestLog();
        }

        return ResponseEntity.ok(Map.of("status", "success", "message", "Data stored successfully"));
    }
}
