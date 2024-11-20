package com.example.odds_receiver.Controller;

import com.example.odds_receiver.Model.CornerMatch;
import com.example.odds_receiver.Model.Match;
import com.example.odds_receiver.Repository.CornerMatchRepository;
import com.example.odds_receiver.Repository.MatchRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class OddsController {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private CornerMatchRepository cornerMatchRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final int MAX_BATCH_COUNT = 12; // 最大批次数

    @PostMapping("/receive_odds_server1")
    @Transactional
    public ResponseEntity<?> receiveOdds(@RequestBody List<Match> matches) {
        if (matches == null || matches.isEmpty()) {
            return ResponseEntity.badRequest().body("No valid data received");
        }

        matches.forEach(match -> {
            match.getOdds().forEach(odd -> odd.setMatch(match)); // 确保每个 Odd 关联到 Match
            matchRepository.save(match); // 级联保存 Match 和 Odds
        });

        manageBatchLimit("system.matches", "system.odds", "match_id");

        return ResponseEntity.ok(Map.of("status", "success", "message", "Data stored successfully"));
    }


    @PostMapping("/receive_corner_odds")
    @Transactional
    public ResponseEntity<?> receiveCornerOdds(@RequestBody List<CornerMatch> matches) {
        if (matches == null || matches.isEmpty()) {
            return ResponseEntity.badRequest().body("No valid data received");
        }

        matches.forEach(match -> {
            match.getOdds().forEach(odd -> odd.setCornerMatch(match)); // 确保每个 CornerOdd 关联到 CornerMatch
            cornerMatchRepository.save(match); // 级联保存 CornerMatch 和 CornerOdds
        });

        manageBatchLimit("system.corner_matches", "system.corner_odds", "corner_match_id");

        return ResponseEntity.ok(Map.of("status", "success", "message", "Corner data stored successfully"));
    }


    private void manageBatchLimit(String matchTable, String oddsTable, String matchIdColumn) {
        // 统计批次数量
        Query batchCountQuery = entityManager.createNativeQuery(
                "SELECT COUNT(DISTINCT inserted_at) FROM " + matchTable
        );
        long batchCount = ((Number) batchCountQuery.getSingleResult()).longValue();

        if (batchCount > MAX_BATCH_COUNT) {
            // 查询最早的批次时间
            Query oldestBatchQuery = entityManager.createNativeQuery(
                    "SELECT inserted_at FROM " + matchTable + " ORDER BY inserted_at ASC LIMIT 1"
            );
            java.sql.Timestamp oldestBatchTimestamp = (java.sql.Timestamp) oldestBatchQuery.getSingleResult();
            LocalDateTime oldestBatchTime = oldestBatchTimestamp.toLocalDateTime();

            // 删除最早批次的赔率数据
            entityManager.createNativeQuery(
                    "DELETE FROM " + oddsTable + " WHERE " + matchIdColumn +
                            " IN (SELECT id FROM " + matchTable + " WHERE inserted_at = :oldestBatchTime)"
            ).setParameter("oldestBatchTime", oldestBatchTime).executeUpdate();

            // 删除最早批次的比赛信息
            entityManager.createNativeQuery(
                    "DELETE FROM " + matchTable + " WHERE inserted_at = :oldestBatchTime"
            ).setParameter("oldestBatchTime", oldestBatchTime).executeUpdate();
        }
    }








}
