package de.agiehl.games.cartographers.database.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Table("PLAYER")
@Data
public class PlayerEntity {

    @Id
    @Setter(AccessLevel.NONE)
    private long id;

    private String gameId;

    private String playerId;

    private String playerName;

    private boolean ready;
}