package com.example.odds_receiver.Repository;

import com.example.odds_receiver.Model.CornerMatch2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CornerMatchRepository2 extends JpaRepository<CornerMatch2, Long> {

    void deleteByLeagueNameAndMatchTimeAndHomeTeamAndAwayTeam(String leagueName, String matchTime, String homeTeam, String awayTeam);

    Optional<CornerMatch2> findByLeagueNameAndMatchTimeAndHomeTeamAndAwayTeam(String leagueName, String matchTime, String homeTeam, String awayTeam);

    @Modifying
    @Query(value = "TRUNCATE TABLE system.corner_matches2 RESTART IDENTITY CASCADE", nativeQuery = true)
    void truncateTable();
}
