package com.example.odds_receiver.Controller;

import com.example.odds_receiver.DTO.CornerMatch2BasicDTO;
import com.example.odds_receiver.DTO.CornerMatchBasicDTO;
import com.example.odds_receiver.DTO.Match2BasicDTO;
import com.example.odds_receiver.DTO.MatchBasicDTO;
import com.example.odds_receiver.Model.*;
import com.example.odds_receiver.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/broadcast")
public class BroadcastController {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private CornerMatchRepository cornerMatchRepository;
    @Autowired
    private MatchRepository2 matchRepository2;
    @Autowired
    private CornerMatchRepository2 cornerMatchRepository2;



    @GetMapping("/matches/basic")
    public ResponseEntity<Map<String, Object>> getAllBasicMatches() {
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

        Map<String, Object> response = new HashMap<>();
        response.put("matchCount", matchBasicDTOs.size());
        response.put("matches", matchBasicDTOs);

        return ResponseEntity.ok(response);
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


    @GetMapping("/matches2/basic")
    public ResponseEntity<Map<String, Object>> getAllBasicMatches2() {
        // 查询所有 Match2
        List<Match2> matches2 = matchRepository2.findAll();

        // 转换为 DTO
        List<Match2BasicDTO> match2DTOs = matches2.stream()
                .map(match -> new Match2BasicDTO(
                        match.getId(),
                        match.getLeagueName(),
                        match.getMatchTime(),
                        match.getHomeTeam(),
                        match.getAwayTeam(),
                        match.getHomeScore(),
                        match.getAwayScore(),
                        match.getInsertedAt()))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("matchCount", match2DTOs.size());
        response.put("matches", match2DTOs);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/corner-matches2/basic")
    public ResponseEntity<List<CornerMatch2BasicDTO>> getAllBasicCornerMatches2() {
        // 查询所有 CornerMatch2
        List<CornerMatch2> cornerMatches2 = cornerMatchRepository2.findAll();

        // 转换为 DTO
        List<CornerMatch2BasicDTO> cornerMatch2DTOs = cornerMatches2.stream()
                .map(match -> new CornerMatch2BasicDTO(
                        match.getId(),
                        match.getLeagueName(),
                        match.getMatchTime(),
                        match.getHomeTeam(),
                        match.getAwayTeam(),
                        match.getHomeScore(),
                        match.getAwayScore(),
                        match.getInsertedAt()))
                .toList();

        return ResponseEntity.ok(cornerMatch2DTOs);
    }






}
