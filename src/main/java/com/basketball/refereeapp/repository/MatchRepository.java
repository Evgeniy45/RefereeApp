package com.basketball.refereeapp.repository;

import com.basketball.refereeapp.model.LeagueLevel;
import com.basketball.refereeapp.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByLeagueLevel(LeagueLevel leagueLevel);

}

