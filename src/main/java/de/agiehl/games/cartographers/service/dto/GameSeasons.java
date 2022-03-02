package de.agiehl.games.cartographers.service.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GameSeasons {

    SPRING((short)0),
    SUMMER((short)1),
    FALL((short)2),
    WINTER((short)3);

    public static GameSeasons getStartSeason() {
        return SPRING;
    }

    private short statusCode;
    
    public static Optional<GameSeasons> findeByStatuCode(short statusCode) {
        for (GameSeasons gs : GameSeasons.values()) {
            if (gs.statusCode == statusCode) {
                return Optional.of(gs);
            }
        }

        return Optional.empty();
    }
}