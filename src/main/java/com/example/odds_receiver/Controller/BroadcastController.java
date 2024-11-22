package com.example.odds_receiver.Controller;

import com.example.odds_receiver.DTO.CornerMatchBasicDTO;
import com.example.odds_receiver.DTO.MatchBasicDTO;
import com.example.odds_receiver.Model.CornerMatch;
import com.example.odds_receiver.Model.Match;
import com.example.odds_receiver.Model.Odd;
import com.example.odds_receiver.Repository.CornerMatchRepository;
import com.example.odds_receiver.Repository.MatchRepository;
import com.example.odds_receiver.Repository.OddRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/broadcast")
public class BroadcastController {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private OddRepository oddRepository;

    @Autowired
    private CornerMatchRepository cornerMatchRepository;



    @GetMapping("/matches/basic")
    public ResponseEntity<List<MatchBasicDTO>> getAllBasicMatches() {
        // 查询所有比赛
        List<Match> matches = matchRepository.findAll();

        // 转换为 DTO
        List<MatchBasicDTO> matchBasicDTOs = matches.stream()
                .map(match -> new MatchBasicDTO(
                        match.getId(),
                        match.getEventId(),
                        match.getLeagueName(),
                        match.getMatchTime(),
                        match.getHomeTeam(),
                        match.getAwayTeam(),
                        match.getHomeScore(),
                        match.getAwayScore()))
                .toList();

        return ResponseEntity.ok(matchBasicDTOs);
    }


    @GetMapping("/corner-matches/basic")
    public ResponseEntity<List<CornerMatchBasicDTO>> getAllCornerBasicMatches() {
        // 查询所有脚球比赛
        List<CornerMatch> cornerMatches = cornerMatchRepository.findAll();

        // 转换为 DTO
        List<CornerMatchBasicDTO> cornerMatchDTOs = cornerMatches.stream()
                .map(match -> new CornerMatchBasicDTO(
                        match.getId(),
                        match.getEventId(),
                        match.getLeagueName(),
                        match.getMatchTime(),
                        match.getHomeTeam(),
                        match.getAwayTeam(),
                        match.getHomeScore(),
                        match.getAwayScore()))
                .toList();

        return ResponseEntity.ok(cornerMatchDTOs);
    }









}
