package com.example.odds_receiver.Service;

import com.example.odds_receiver.Model.Match2;
import com.example.odds_receiver.Repository.MatchRepository;
import com.example.odds_receiver.Repository.MatchRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatchService2 {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchRepository2 matchRepository2;

    @Transactional
    public void replaceAllMatches(List<Match2> matches) {
        // 清空表
        matchRepository2.truncateTable();

        // 插入新数据
        matchRepository2.saveAll(matches);
    }
}
