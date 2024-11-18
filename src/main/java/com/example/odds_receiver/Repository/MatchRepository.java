package com.example.odds_receiver.Repository;

import com.example.odds_receiver.Model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MatchRepository extends JpaRepository<Match, Long> {

    @Modifying
    @Query(value = "DELETE FROM system.matches WHERE id IN (SELECT id FROM system.matches ORDER BY inserted_at ASC LIMIT 1)", nativeQuery = true)
    void deleteOldestMatch();


}
