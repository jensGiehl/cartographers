package de.agiehl.games.cartographers.service.dto;

import java.util.Optional;

import lombok.Data;

@Data
public class Player {

    private Optional<String> playerId;

    private String playerName;

    private boolean ready;

}
