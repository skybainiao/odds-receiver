package com.example.odds_receiver.Controller;

import com.example.odds_receiver.Model.CornerMatch;
import com.example.odds_receiver.Model.CornerOdd;
import com.example.odds_receiver.Model.Match;
import com.example.odds_receiver.Model.Odd;
import com.example.odds_receiver.Repository.CornerMatchRepository;
import com.example.odds_receiver.Repository.MatchRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class OddsController {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private CornerMatchRepository cornerMatchRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final int MAX_BATCH_COUNT = 12; // 最大批次数

    private static final Logger logger = LoggerFactory.getLogger(OddsController.class);

    /**
     * 接收普通比赛赔率数据
     */
    @PostMapping("/receive_odds_server1")
    @Transactional
    public ResponseEntity<?> receiveOdds(@RequestBody List<Match> incomingMatches) {
        if (incomingMatches == null || incomingMatches.isEmpty()) {
            return ResponseEntity.badRequest().body("No valid data received");
        }

        // 当前批次时间，截断到秒
        LocalDateTime batchTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        logger.info("Receiving odds batch at {}", batchTime);

        for (Match incomingMatch : incomingMatches) {
            // 根据 eventId 查找现有 Match
            Optional<Match> optionalMatch = matchRepository.findByEventId(incomingMatch.getEventId());

            Match match;
            if (optionalMatch.isPresent()) {
                match = optionalMatch.get();
                // 更新 Match 字段
                match.setLeagueName(incomingMatch.getLeagueName());
                match.setMatchTime(incomingMatch.getMatchTime());
                match.setHomeTeam(incomingMatch.getHomeTeam());
                match.setAwayTeam(incomingMatch.getAwayTeam());
                match.setHomeScore(incomingMatch.getHomeScore());
                match.setAwayScore(incomingMatch.getAwayScore());
                logger.info("Updating existing Match with eventId {}", incomingMatch.getEventId());
            } else {
                // 创建新的 Match
                match = new Match();
                match.setEventId(incomingMatch.getEventId());
                match.setLeagueName(incomingMatch.getLeagueName());
                match.setMatchTime(incomingMatch.getMatchTime());
                match.setHomeTeam(incomingMatch.getHomeTeam());
                match.setAwayTeam(incomingMatch.getAwayTeam());
                match.setHomeScore(incomingMatch.getHomeScore());
                match.setAwayScore(incomingMatch.getAwayScore());
                logger.info("Creating new Match with eventId {}", incomingMatch.getEventId());
            }

            // 处理 Odds
            List<Odd> incomingOdds = incomingMatch.getOdds();
            if (incomingOdds != null) {
                for (Odd incomingOdd : incomingOdds) {
                    Odd newOdd = new Odd();
                    newOdd.setMatch(match); // 关联到 Match
                    newOdd.setBetType(incomingOdd.getBetType());
                    newOdd.setPeriodNumber(incomingOdd.getPeriodNumber());
                    newOdd.setHdp(incomingOdd.getHdp());
                    newOdd.setPoints(incomingOdd.getPoints());
                    newOdd.setHomeOdds(incomingOdd.getHomeOdds());
                    newOdd.setAwayOdds(incomingOdd.getAwayOdds());
                    newOdd.setDrawOdds(incomingOdd.getDrawOdds());
                    newOdd.setOverOdds(incomingOdd.getOverOdds());
                    newOdd.setUnderOdds(incomingOdd.getUnderOdds());
                    newOdd.setInsertedAt(batchTime); // 设置插入时间为批次时间

                    // 添加到 Match 的 odds 列表
                    match.getOdds().add(newOdd);
                    logger.debug("Added Odd with insertedAt {} to Match {}", batchTime, match.getEventId());
                }
            }

            // 保存 Match 和关联的 Odds
            matchRepository.save(match);
            logger.info("Saved Match with eventId {}", match.getEventId());
        }

        // 管理批次数量
        manageBatchLimit("odds");

        return ResponseEntity.ok(Map.of("status", "success", "message", "Data stored successfully"));
    }

    /**
     * 接收脚球比赛赔率数据
     */
    @PostMapping("/receive_corner_odds")
    @Transactional
    public ResponseEntity<?> receiveCornerOdds(@RequestBody List<CornerMatch> incomingCornerMatches) {
        if (incomingCornerMatches == null || incomingCornerMatches.isEmpty()) {
            return ResponseEntity.badRequest().body("No valid data received");
        }

        // 当前批次时间，截断到秒
        LocalDateTime batchTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        logger.info("Receiving corner odds batch at {}", batchTime);

        for (CornerMatch incomingMatch : incomingCornerMatches) {
            // 根据 eventId 查找现有 CornerMatch
            Optional<CornerMatch> optionalMatch = cornerMatchRepository.findByEventId(incomingMatch.getEventId());

            CornerMatch match;
            if (optionalMatch.isPresent()) {
                match = optionalMatch.get();
                // 更新 CornerMatch 字段
                match.setLeagueName(incomingMatch.getLeagueName());
                match.setMatchTime(incomingMatch.getMatchTime());
                match.setHomeTeam(incomingMatch.getHomeTeam());
                match.setAwayTeam(incomingMatch.getAwayTeam());
                match.setHomeScore(incomingMatch.getHomeScore());
                match.setAwayScore(incomingMatch.getAwayScore());
                logger.info("Updating existing CornerMatch with eventId {}", incomingMatch.getEventId());
            } else {
                // 创建新的 CornerMatch
                match = new CornerMatch();
                match.setEventId(incomingMatch.getEventId());
                match.setLeagueName(incomingMatch.getLeagueName());
                match.setMatchTime(incomingMatch.getMatchTime());
                match.setHomeTeam(incomingMatch.getHomeTeam());
                match.setAwayTeam(incomingMatch.getAwayTeam());
                match.setHomeScore(incomingMatch.getHomeScore());
                match.setAwayScore(incomingMatch.getAwayScore());
                logger.info("Creating new CornerMatch with eventId {}", incomingMatch.getEventId());
            }

            // 处理 CornerOdds
            List<CornerOdd> incomingOdds = incomingMatch.getOdds();
            if (incomingOdds != null) {
                for (CornerOdd incomingOdd : incomingOdds) {
                    CornerOdd newOdd = new CornerOdd();
                    newOdd.setCornerMatch(match); // 关联到 CornerMatch
                    newOdd.setBetType(incomingOdd.getBetType());
                    newOdd.setPeriodNumber(incomingOdd.getPeriodNumber());
                    newOdd.setHdp(incomingOdd.getHdp());
                    newOdd.setPoints(incomingOdd.getPoints());
                    newOdd.setHomeOdds(incomingOdd.getHomeOdds());
                    newOdd.setAwayOdds(incomingOdd.getAwayOdds());
                    newOdd.setOverOdds(incomingOdd.getOverOdds());
                    newOdd.setUnderOdds(incomingOdd.getUnderOdds());
                    newOdd.setInsertedAt(batchTime); // 设置插入时间为批次时间

                    // 添加到 CornerMatch 的 odds 列表
                    match.getOdds().add(newOdd);
                    logger.debug("Added CornerOdd with insertedAt {} to CornerMatch {}", batchTime, match.getEventId());
                }
            }

            // 保存 CornerMatch 和关联的 CornerOdds
            cornerMatchRepository.save(match);
            logger.info("Saved CornerMatch with eventId {}", match.getEventId());
        }

        // 管理批次数量
        manageBatchLimit("corner_odds");

        return ResponseEntity.ok(Map.of("status", "success", "message", "Corner data stored successfully"));
    }

    /**
     * 管理赔率数据的批次数量，确保每种类型的批次数量不超过 MAX_BATCH_COUNT。
     * 如果超过，则删除最早的批次及其关联的 Odds 数据。
     *
     * @param oddsType 赔率类型，"odds" 或 "corner_odds"
     */
    private void manageBatchLimit(String oddsType) {
        String tableName;
        String matchTableName;
        String matchColumn;

        if ("odds".equals(oddsType)) {
            tableName = "system.odds";
            matchTableName = "system.matches";
            matchColumn = "match_id";
        } else if ("corner_odds".equals(oddsType)) {
            tableName = "system.corner_odds";
            matchTableName = "system.corner_matches";
            matchColumn = "corner_match_id";
        } else {
            throw new IllegalArgumentException("Invalid odds type: " + oddsType);
        }

        // 统计不同批次的数量，基于 inserted_at 截断到秒
        String countDistinctBatchQuery = "SELECT COUNT(DISTINCT date_trunc('second', inserted_at)) FROM " + tableName;
        Query batchCountQuery = entityManager.createNativeQuery(countDistinctBatchQuery);
        long batchCount = ((Number) batchCountQuery.getSingleResult()).longValue();

        logger.info("Current batch count for type '{}': {}", oddsType, batchCount);

        if (batchCount > MAX_BATCH_COUNT) {
            // 获取最早的批次时间
            String oldestBatchQueryStr = "SELECT date_trunc('second', inserted_at) FROM " + tableName +
                    " ORDER BY date_trunc('second', inserted_at) ASC LIMIT 1";

            Query oldestBatchQuery = entityManager.createNativeQuery(oldestBatchQueryStr);
            Object oldestBatchTimeObj = oldestBatchQuery.getSingleResult();
            LocalDateTime oldestBatchTime;

            if (oldestBatchTimeObj instanceof java.sql.Timestamp) {
                oldestBatchTime = ((java.sql.Timestamp) oldestBatchTimeObj).toLocalDateTime();
            } else {
                oldestBatchTime = LocalDateTime.parse(oldestBatchTimeObj.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }

            logger.info("Deleting earliest batch time: {}", oldestBatchTime);

            // 删除最早批次的 Odds 数据
            String deleteOddsQueryStr = "DELETE FROM " + tableName +
                    " WHERE inserted_at = :oldestBatchTime";

            Query deleteOddsQuery = entityManager.createNativeQuery(deleteOddsQueryStr)
                    .setParameter("oldestBatchTime", oldestBatchTime);
            int deletedOdds = deleteOddsQuery.executeUpdate();

            logger.info("Deleted {} records from {}", deletedOdds, tableName);

            // 删除与批次关联的 Match 或 CornerMatch 数据
            String deleteMatchQueryStr = "DELETE FROM " + matchTableName +
                    " WHERE id IN (SELECT " + (oddsType.equals("odds") ? "match_id" : "corner_match_id") + " FROM " + tableName +
                    " WHERE inserted_at = :oldestBatchTime)";

            Query deleteMatchQuery = entityManager.createNativeQuery(deleteMatchQueryStr)
                    .setParameter("oldestBatchTime", oldestBatchTime);
            int deletedMatches = deleteMatchQuery.executeUpdate();

            logger.info("Deleted {} records from {}", deletedMatches, matchTableName);
        } else {
            logger.info("No need to delete batches for type '{}'.", oddsType);
        }
    }
}
