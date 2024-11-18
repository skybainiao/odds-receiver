package com.example.odds_receiver.Repository;

import com.example.odds_receiver.Model.Odd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OddRepository extends JpaRepository<Odd, Long> {
}
