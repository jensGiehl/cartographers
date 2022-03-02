package de.agiehl.games.cartographers.database.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Table("SCORE")
@Data
public class ScoreEntity {

    @Id
    @Setter(AccessLevel.NONE)
    private long id;

    private String gameId;

    private String playerId;

    private int springMission1;

    private int springMission2;

    private int springGold;

    private int springMonsters;
    
    private int summerMission1;

    private int summerMission2;

    private int summerGold;

    private int summerMonsters;

    private int fallMission1;

    private int fallMission2;

    private int fallGold;

    private int fallMonsters;

    private int winterMission1;

    private int winterMission2;

    private int winterGold;

    private int winterMonsters;

}