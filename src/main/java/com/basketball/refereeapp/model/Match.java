package com.basketball.refereeapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Назва домашньої команди не може бути порожньою")
    private String homeTeam;

    @NotBlank(message = "Назва гостьової команди не може бути порожньою")
    private String awayTeam;

    @NotNull(message = "Дата матчу обов'язкова")
    private LocalDateTime matchDate;

    @NotNull(message = "Рівень ліги обов'язковий")
    @Enumerated(EnumType.STRING)
    private LeagueLevel leagueLevel;
}
