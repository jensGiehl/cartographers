package de.agiehl.games.cartographers.service;

import static de.agiehl.games.cartographers.service.dto.GameSeasons.getStartSeason;
import static de.agiehl.games.cartographers.service.dto.GameStatus.OPEN;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.agiehl.games.cartographers.database.GameRepository;
import de.agiehl.games.cartographers.database.entities.GameEntity;
import de.agiehl.games.cartographers.service.dto.Game;
import de.agiehl.games.cartographers.service.dto.GameCounter;
import de.agiehl.games.cartographers.service.dto.GameSeasons;
import de.agiehl.games.cartographers.service.dto.GameStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GameService {

    private GameRepository repro;

    public Game createGame() {
        String adminToken = randomAlphanumeric(25);
        String gameId = randomAlphanumeric(8);
        GameStatus status = OPEN;
        GameSeasons season = getStartSeason();

        GameEntity gameEntity = new GameEntity();
        gameEntity.setAdminToken(adminToken);
        gameEntity.setGameId(gameId);
        gameEntity.setStartedAt(new Date());
        gameEntity.setGameStatus(status.getStatusCode());
        gameEntity.setHourglassesRevealed((short) 0);
        gameEntity.setCurrentSeason(season.getStatusCode());

        gameEntity = repro.save(gameEntity);

        log.info("Spiel mit der ID {} erstellt (Spiel-ID: {})", gameEntity.getId(), gameEntity.getGameId());

        Game game = new Game();
        game.setAdminToken(Optional.of(adminToken));
        game.setGameId(gameId);
        game.setHourglassesRevealed(gameEntity.getHourglassesRevealed());
        game.setSeason(season);
        game.setStatus(status);

        return game;
    }

    public void startGame(String gameId) {
        Optional<GameEntity> game =  repro.findGameByGameId(gameId);
        if (game.isPresent() && game.get().getGameStatus() == GameStatus.OPEN.getStatusCode()) {
            GameEntity gameEntity = game.get();
            gameEntity.setStartedAt(new Date());
            gameEntity.setGameStatus(GameStatus.STARTED.getStatusCode());

            repro.save(gameEntity);
        }
    }

	public GameCounter countGames() {

        long totalCount = repro.count();
        long activeCount = repro.countWithStatus(GameStatus.STARTED.getStatusCode());

		return new GameCounter(activeCount, totalCount);
	}

}