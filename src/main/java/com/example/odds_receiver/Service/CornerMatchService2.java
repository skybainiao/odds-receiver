package com.example.odds_receiver.Service;

import com.example.odds_receiver.Model.CornerMatch2;
import com.example.odds_receiver.Repository.CornerMatchRepository;
import com.example.odds_receiver.Repository.CornerMatchRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CornerMatchService2 {

    @Autowired
    private CornerMatchRepository2 cornerMatchRepository2;

    @Transactional
    public void saveOrUpdateMatches(List<CornerMatch2> matches) {
        for (CornerMatch2 match : matches) {
            // 清空相关比赛数据
            cornerMatchRepository2.deleteByLeagueNameAndMatchTimeAndHomeTeamAndAwayTeam(
                    match.getLeagueName(),
                    match.getMatchTime(),
                    match.getHomeTeam(),
                    match.getAwayTeam()
            );

            // 保存新数据
            cornerMatchRepository2.save(match);
        }
    }
}
