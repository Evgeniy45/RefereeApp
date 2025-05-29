package com.basketball.refereeapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String homeTeam;
    private String awayTeam;

    private LocalDateTime matchDate;

    @Enumerated(EnumType.STRING)
    private LeagueLevel leagueLevel;

}
