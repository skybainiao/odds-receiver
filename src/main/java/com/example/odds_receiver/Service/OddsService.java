package com.example.odds_receiver.Service;

import com.example.odds_receiver.Model.MatchDataServer2;
import com.example.odds_receiver.Model.OddsDataServer1;
import com.example.odds_receiver.Model.OddsDataServer1.EventData;
import com.example.odds_receiver.Model.OddsDataServer1.OddsInfo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OddsService {

    // 存储历史赔率数据
    private final Map<String, List<OddsRecord>> oddsHistory = new ConcurrentHashMap<>();

    // 存储已播报的涨幅信息
    private final Set<String> reportedOdds = ConcurrentHashMap.newKeySet();

    // 涨幅阈值
    private static final double THRESHOLD = 0.10; // 10%

    // 时间间隔（秒）
    private static final int[] INTERVALS = {10, 15, 20};

    // 定义赔率记录的内部类
    private static class OddsRecord {
        Instant timestamp;
        Double oddsValue;

        OddsRecord(Instant timestamp, Double oddsValue) {
            this.timestamp = timestamp;
            this.oddsValue = oddsValue;
        }
    }

    // 处理来自 1 号服务器的数据
    /*
    public void processServer1Data(OddsDataServer1 data) {
        Instant now = Instant.now();

        // 遍历所有联赛
        for (OddsDataServer1.LeagueData league : data.getLeagues().values()) { // 修改为data.getLeagues().values()
            // 遍历所有比赛
            for (EventData event : league.getEvents()) {
                String eventId = String.valueOf(event.getEvent_id()); // 将Long类型转换为String
                String homeTeam = event.getHome_team();
                String awayTeam = event.getAway_team();

                // 如果需要翻译球队名称，可以在这里进行
                String homeTeamChinese = translateTeamName(homeTeam);
                String awayTeamChinese = translateTeamName(awayTeam);

                // 遍历所有赔率信息
                for (OddsInfo oddsInfo : event.getOdds()) {
                    String betType = oddsInfo.getBetType();

                    // 处理 MONEYLINE（独赢）赔率
                    if ("MONEYLINE".equals(betType)) {
                        String betTypeChinese = "独赢";
                        String betTypeEnglish = "Moneyline";

                        // 主胜
                        if (oddsInfo.getHomeOdds() != null) {
                            String key = eventId + "_MONEYLINE_HOME";
                            Double currentOdds = oddsInfo.getHomeOdds();

                            String teamType = "主队";
                            String teamName = homeTeam;

                            updateOddsHistory(key, currentOdds, now, betTypeChinese, betTypeEnglish, teamType, teamName);
                        }
                        // 平局
                        if (oddsInfo.getDrawOdds() != null) {
                            String key = eventId + "_MONEYLINE_DRAW";
                            Double currentOdds = oddsInfo.getDrawOdds();

                            String teamType = "平局";
                            String teamName = homeTeam + " vs " + awayTeam;

                            updateOddsHistory(key, currentOdds, now, betTypeChinese, betTypeEnglish, teamType, teamName);
                        }
                        // 客胜
                        if (oddsInfo.getAwayOdds() != null) {
                            String key = eventId + "_MONEYLINE_AWAY";
                            Double currentOdds = oddsInfo.getAwayOdds();

                            String teamType = "客队";
                            String teamName = awayTeam;

                            updateOddsHistory(key, currentOdds, now, betTypeChinese, betTypeEnglish, teamType, teamName);
                        }
                    }

                    // TODO: 处理其他赔率类型，例如 SPREAD, TOTAL_POINTS 等
                }
            }
        }

        // 清理过期的数据
        cleanUpOldData(now);
    }
*/

    // 更新赔率历史数据并检测涨幅
    private void updateOddsHistory(String key, Double currentOdds, Instant now, String betTypeChinese, String betTypeEnglish, String teamType, String teamName) {
        // 获取或创建盘口的历史数据列表
        oddsHistory.putIfAbsent(key, new ArrayList<>());
        List<OddsRecord> records = oddsHistory.get(key);

        // 添加当前赔率记录
        records.add(new OddsRecord(now, currentOdds));

        // 对历史记录按时间排序
        records.sort(Comparator.comparing(record -> record.timestamp));

        // 检测涨幅
        for (int interval : INTERVALS) {
            Instant pastTime = now.minusSeconds(interval);
            OddsRecord pastRecord = null;

            // 从最新的记录开始遍历，找到在 pastTime 之前的最近一条记录
            for (int i = records.size() - 1; i >= 0; i--) {
                OddsRecord record = records.get(i);
                if (!record.timestamp.isAfter(pastTime)) {
                    pastRecord = record;
                    break;
                }
            }

            if (pastRecord != null) {
                double percentageChange = (currentOdds - pastRecord.oddsValue) / pastRecord.oddsValue;

                if (percentageChange >= THRESHOLD) {
                    // 生成唯一的键，防止重复播报
                    String reportKey = key + "_" + interval;

                    // 检查是否已经播报过
                    if (!reportedOdds.contains(reportKey)) {
                        // 标记为已播报
                        reportedOdds.add(reportKey);

                        String info = String.format("盘口: %s (%s), 类型: %s, %s%s, 时间间隔: %d 秒, 赔率从 %.2f 涨至 %.2f, 涨幅: %.2f%%",
                                betTypeChinese, betTypeEnglish, teamType, teamName.equals("无") ? "" : "球队: ", teamName, interval, pastRecord.oddsValue, currentOdds, percentageChange * 100);
                        System.out.println(info);
                    }
                }
            }
        }
    }

    // 清理超过 20 秒的历史数据
    private void cleanUpOldData(Instant now) {
        for (List<OddsRecord> records : oddsHistory.values()) {
            records.removeIf(record -> record.timestamp.isBefore(now.minusSeconds(20)));
        }
    }

    // 清理已播报的记录（需要实现过期逻辑）
    private void cleanUpReportedOdds(Instant now) {
        // TODO: 根据需要实现已播报记录的过期清理
    }

    // 模拟翻译球队名称的函数
    private String translateTeamName(String teamName) {
        Map<String, String> translationMap = Map.of(
                "Lanus", "拉努斯",
                "Platense", "普拉滕塞"
                // 更多球队翻译
        );
        return translationMap.getOrDefault(teamName, teamName);
    }

    // 处理来自 2 号服务器的数据（如果需要）
    public void processServer2Data(List<MatchDataServer2> data) {
        // TODO: 根据需要处理 2 号服务器的数据
    }
}