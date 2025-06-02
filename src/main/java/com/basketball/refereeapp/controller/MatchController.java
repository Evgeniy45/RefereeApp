package com.basketball.refereeapp.controller;

import com.basketball.refereeapp.model.LeagueLevel;
import com.basketball.refereeapp.model.Match;
import com.basketball.refereeapp.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matches")
@Tag(name = "Match Controller", description = "Керування матчами")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @Operation(summary = "Отримати всі матчі")
    @GetMapping
    public ResponseEntity<List<Match>> getAll() {
        return ResponseEntity.ok(matchService.getAllMatches());
    }

    @Operation(summary = "Створити новий матч")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Match match, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            return ResponseEntity.badRequest().body("Помилка валідації: " + errors);
        }
        return ResponseEntity.ok(matchService.createMatch(match));
    }

    @Operation(summary = "Отримати матч за ID")
    @GetMapping("/{id}")
    public ResponseEntity<Match> getById(@PathVariable Long id) {
        return matchService.getMatchById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Фільтрувати матчі за рівнем ліги")
    @GetMapping("/filter")
    public ResponseEntity<List<Match>> getByLeagueLevel(@RequestParam LeagueLevel level) {
        return ResponseEntity.ok(matchService.getMatchesByLeagueLevel(level));
    }
}
