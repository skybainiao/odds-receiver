package com.example.odds_receiver.Repository;

import com.example.odds_receiver.Model.CornerMatch2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CornerMatchRepository2 extends JpaRepository<CornerMatch2, Long> {

    void deleteByLeagueNameAndMatchTimeAndHomeTeamAndAwayTeam(String leagueName, String matchTime, String homeTeam, String awayTeam);
}
