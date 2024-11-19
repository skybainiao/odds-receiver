package com.example.odds_receiver.Repository;

import com.example.odds_receiver.Model.CornerMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CornerMatchRepository extends JpaRepository<CornerMatch, Long> {

    @Modifying
    @Query(value = "DELETE FROM system.corner_matches WHERE event_id IN " +
            "(SELECT event_id FROM system.corner_matches ORDER BY inserted_at ASC LIMIT 1)", nativeQuery = true)
    void deleteOldestCornerMatch();
}
