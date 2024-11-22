package com.example.odds_receiver.Repository;

import com.example.odds_receiver.Model.Odd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OddRepository extends JpaRepository<Odd, Long> {
    List<Odd> findByMatchId(Long matchId);
}
