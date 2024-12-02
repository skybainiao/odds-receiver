package com.example.odds_receiver.Repository;

import com.example.odds_receiver.Model.Match;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {

    @Modifying
    @Query(value = "DELETE FROM system.matches WHERE event_id IN " +
            "(SELECT event_id FROM system.matches ORDER BY inserted_at ASC LIMIT 1)", nativeQuery = true)
    void deleteMatchesByOldestLog();

    @Modifying
    @Query(value = "DELETE FROM system.match_store_logs WHERE id = (SELECT id FROM system.match_store_logs ORDER BY id ASC LIMIT 1)", nativeQuery = true)
    void deleteOldestMatchLog();

    @Modifying
    @Query(value = "DELETE FROM system.odds WHERE event_id IN (SELECT event_id FROM system.matches ORDER BY inserted_at ASC LIMIT 1)", nativeQuery = true)
    void deleteOddsByOldestMatch();

    Optional<Match> findByEventId(Long eventId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Match m WHERE m.eventId NOT IN :eventIds")
    void deleteMatchesNotIn(@Param("eventIds") List<Long> eventIds);

}
