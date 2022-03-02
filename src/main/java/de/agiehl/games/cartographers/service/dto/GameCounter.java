package de.agiehl.games.cartographers.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameCounter {

    private long activeGames;
    private long totalGames;

}