package com.example.odds_receiver.Repository;

import com.example.odds_receiver.Model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;

public interface MatchRepository extends JpaRepository<Match, Long> {

    @Modifying
    @Query(value = "DELETE FROM system.matches WHERE inserted_at < (SELECT operation_time FROM system.store_logs ORDER BY id ASC LIMIT 1)", nativeQuery = true)
    void deleteMatchesByOldestLog();


    @Modifying
    @Query(value = "DELETE FROM system.store_logs WHERE id = (SELECT id FROM system.store_logs ORDER BY id ASC LIMIT 1)", nativeQuery = true)
    void deleteOldestLog();

}
