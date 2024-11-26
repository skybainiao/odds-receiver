// src/main/java/com/example/autobet/repository/BindingRepository.java

package com.example.odds_receiver.Repository;


import com.example.odds_receiver.Model.Binding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BindingRepository extends JpaRepository<Binding, Long> {
    boolean existsByLeague1NameAndLeague2NameAndHomeTeam1NameAndHomeTeam2NameAndAwayTeam1NameAndAwayTeam2Name(
            String league1Name,
            String league2Name,
            String homeTeam1Name,
            String homeTeam2Name,
            String awayTeam1Name,
            String awayTeam2Name
    );
}

