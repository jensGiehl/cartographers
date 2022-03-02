package de.agiehl.games.cartographers.database.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Table("PLAYED_CARD")
@Data
public class PlayedCardEntity {

    @Id
    @Setter(AccessLevel.NONE)
    private long id;

    private String gameId;

    private int cardOrder;

    private int cardId;

    private boolean withRuines;

    private short playedInSeason;


}