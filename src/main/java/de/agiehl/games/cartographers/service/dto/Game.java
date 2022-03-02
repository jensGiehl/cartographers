package de.agiehl.games.cartographers.service.dto;

import java.util.Optional;

import lombok.Data;

@Data
public class Game {

    private String gameId;

    private Optional<String> adminToken;

    private GameStatus status;

    private GameSeasons season;

    private short hourglassesRevealed;

}
