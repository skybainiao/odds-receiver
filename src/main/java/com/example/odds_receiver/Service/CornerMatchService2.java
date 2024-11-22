package com.example.odds_receiver.Service;

import com.example.odds_receiver.Model.CornerMatch2;
import com.example.odds_receiver.Repository.CornerMatchRepository;
import com.example.odds_receiver.Repository.CornerMatchRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CornerMatchService2 {

    @Autowired
    private CornerMatchRepository2 cornerMatchRepository2;

    @Transactional
    public void saveOrUpdateMatches(List<CornerMatch2> matches) {
        for (CornerMatch2 match : matches) {
            // 查找是否存在匹配的记录
            Optional<CornerMatch2> existingMatch = cornerMatchRepository2.findByLeagueNameAndMatchTimeAndHomeTeamAndAwayTeam(
                    match.getLeagueName(),
                    match.getMatchTime(),
                    match.getHomeTeam(),
                    match.getAwayTeam()
            );

            if (existingMatch.isPresent()) {
                // 更新现有记录
                CornerMatch2 updatedMatch = existingMatch.get();
                updatedMatch.setHomeScore(match.getHomeScore());
                updatedMatch.setAwayScore(match.getAwayScore());
                updatedMatch.setOdds(match.getOdds());
                // 其他需要更新的字段...

                cornerMatchRepository2.save(updatedMatch);
            } else {
                // 插入新记录
                cornerMatchRepository2.save(match);
            }
        }
    }


    @Transactional
    public void replaceAllCornerMatches(List<CornerMatch2> matches) {
        cornerMatchRepository2.truncateTable(); // 需要在 CornerMatchRepository2 中添加此方法
        cornerMatchRepository2.saveAll(matches);
    }

}
