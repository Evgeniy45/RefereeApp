package com.basketball.refereeapp.controller;

import com.basketball.refereeapp.model.LeagueLevel;
import com.basketball.refereeapp.model.Match;
import com.basketball.refereeapp.service.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public List<Match> getAll() {
        return matchService.getAllMatches();
    }

    @PostMapping
    public Match create(@RequestBody Match match) {
        return matchService.createMatch(match);
    }

    @GetMapping("/{id}")
    public Match getById(@PathVariable Long id) {
        return matchService.getMatchById(id);
    }
    @GetMapping("/filter")
    public List<Match> getByLeagueLevel(@RequestParam LeagueLevel level) {
        return matchService.getMatchesByLeagueLevel(level);
    }

}
