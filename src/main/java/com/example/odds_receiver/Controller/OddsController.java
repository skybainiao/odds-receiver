package com.example.odds_receiver.Controller;

import com.example.odds_receiver.Model.Match;
import com.example.odds_receiver.Repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OddsController {

    @Autowired
    private MatchRepository matchRepository;

    @PostMapping("/receive_odds_server1")
    @Transactional
    public ResponseEntity<?> receiveOdds(@RequestBody List<Match> matches) {
        if (matches == null || matches.isEmpty()) {
            return ResponseEntity.badRequest().body("No valid data received");
        }
        for (int i = 0; i < matches.size(); i++) {
            System.out.println(matches.get(i).getLeagueName());
        }

        //matches.forEach(match -> {

           // match.getOdds().forEach(odd -> odd.setMatch(match));
          //  matchRepository.save(match);
        //});

        // 检查记录数量是否超过12条
        //long count = matchRepository.count();
        //while (count > 12) {
           // matchRepository.deleteOldestMatch();
           // count--;
       // }

        return ResponseEntity.ok("Data stored successfully");
    }
}
