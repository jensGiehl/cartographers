package de.agiehl.games.cartographers;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.agiehl.games.cartographers.database.GameRepository;
import de.agiehl.games.cartographers.database.PlayedCardRepository;
import de.agiehl.games.cartographers.database.PlayerRepository;
import de.agiehl.games.cartographers.database.ScoreRepository;
import de.agiehl.games.cartographers.database.entities.GameEntity;
import de.agiehl.games.cartographers.database.entities.PlayedCardEntity;
import de.agiehl.games.cartographers.database.entities.PlayerEntity;
import de.agiehl.games.cartographers.database.entities.ScoreEntity;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Application.class })
public class LearningTestCreateAndSaveEntities {

    @Autowired
    private GameRepository gameRepro;

    @Autowired
    private PlayerRepository playerRepro;

    @Autowired
    private PlayedCardRepository playedCardRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Test
    public void persist_Game() {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setAdminToken("admin token");
        gameEntity.setCurrentSeason((short)0);
        gameEntity.setGameId("4711");
        gameEntity.setGameStatus((short)1);
        gameEntity.setHourglassesRevealed((short) 5);
        gameEntity.setStartedAt(new Date());
        
        System.out.println( gameRepro.save(gameEntity));

        
        PlayerEntity p1 = new PlayerEntity();
        p1.setGameId(gameEntity.getGameId());
        p1.setPlayerId("test");
        p1.setPlayerName("Test");
        p1.setReady(true);

        System.out.println( playerRepro.save(p1) );


        PlayedCardEntity card1 = new PlayedCardEntity();
        card1.setCardId(4711);
        card1.setCardOrder(0);
        card1.setGameId(gameEntity.getGameId());
        card1.setPlayedInSeason((short)0);
        card1.setWithRuines(false);
        
        System.out.println( playedCardRepository.save(card1) );


        ScoreEntity score = new ScoreEntity();
        score.setFallGold(10);
        score.setPlayerId(p1.getPlayerId());
        score.setGameId(gameEntity.getGameId());

        System.out.println( scoreRepository.save(score));
    }

}