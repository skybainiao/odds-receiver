package com.example.odds_receiver.Repository;

import com.example.odds_receiver.Model.Match2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository2 extends JpaRepository<Match2, Long> {

    @Modifying
    @Query(value = "TRUNCATE TABLE system.matches2 RESTART IDENTITY CASCADE", nativeQuery = true)
    void truncateTable();
}
