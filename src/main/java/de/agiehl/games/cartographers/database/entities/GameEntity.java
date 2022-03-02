package de.agiehl.games.cartographers.database.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Table("GAME")
@Data
public class GameEntity {

    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    private String gameId;

    private String adminToken;

    private Date startedAt;

    private short gameStatus;

    private short currentSeason;

    private short hourglassesRevealed;

}