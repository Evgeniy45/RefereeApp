package com.basketball.refereeapp.service;

import com.basketball.refereeapp.model.LeagueLevel;
import com.basketball.refereeapp.model.Match;
import com.basketball.refereeapp.repository.MatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;

    @Test
    void testGetAllMatches() {
        when(matchRepository.findAll()).thenReturn(List.of(new Match()));
        List<Match> result = matchService.getAllMatches();
        assertEquals(1, result.size());
    }

    @Test
    void testGetMatchById_Found() {
        Match match = new Match();
        match.setId(1L);
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));
        assertTrue(matchService.getMatchById(1L).isPresent());
    }

    @Test
    void testGetMatchById_NotFound() {
        when(matchRepository.findById(99L)).thenReturn(Optional.empty());
        assertTrue(matchService.getMatchById(99L).isEmpty());
    }

    @Test
    void testGetByLeagueLevel() {
        when(matchRepository.findByLeagueLevel(LeagueLevel.ВЮБЛ)).thenReturn(List.of(new Match()));
        assertFalse(matchService.getMatchesByLeagueLevel(LeagueLevel.ВЮБЛ).isEmpty());
    }
}