package de.agiehl.games.cartographers.service.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GameStatus {

    OPEN((short)0),
    STARTED((short)1),
    ENDED((short)-1);

    private short statusCode;

    public static Optional<GameStatus> findeByStatuCode(short statusCode) {
        for (GameStatus gs : GameStatus.values()) {
            if (gs.statusCode == statusCode) {
                return Optional.of(gs);
            }
        }

        return Optional.empty();
    }

}