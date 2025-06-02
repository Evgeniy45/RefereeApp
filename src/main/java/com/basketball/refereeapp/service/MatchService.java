package com.basketball.refereeapp.service;

import com.basketball.refereeapp.model.LeagueLevel;
import com.basketball.refereeapp.model.Match;
import com.basketball.refereeapp.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

    public Optional<Match> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    public List<Match> getMatchesByLeagueLevel(LeagueLevel level) {
        return matchRepository.findByLeagueLevel(level);
    }
}
