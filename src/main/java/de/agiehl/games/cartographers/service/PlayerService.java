package de.agiehl.games.cartographers.service;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import de.agiehl.games.cartographers.database.PlayerRepository;
import de.agiehl.games.cartographers.database.entities.PlayerEntity;
import de.agiehl.games.cartographers.service.dto.Player;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerService {

    private PlayerRepository repro;

    public Player createPlayer(@NotBlank String playerName, @NotBlank String gameId) {
        String playerId = randomAlphanumeric(8);
        playerName = StringUtils.abbreviate(playerName, 50);

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setGameId(gameId);
        playerEntity.setPlayerName(playerName);
        playerEntity.setReady(false);
        playerEntity.setPlayerId(playerId);

        playerEntity = repro.save(playerEntity);

        log.info("Player with ID {} created. Valid for game {} (Player-ID: {})", playerEntity.getId(), gameId,
                playerId);

        Player player = new Player();
        player.setPlayerId(Optional.of(playerId));
        player.setPlayerName(playerName);
        player.setReady(playerEntity.isReady());

        return player;
    }

    public List<Player> listAllPlayers(@NotNull String gameId) {
        List<Player> players = repro.findAllByGameId(gameId).stream().map(entity -> createPlayerFromEntity(entity)).collect(toList());
        
        log.info("Found {} players for Game {}", players.size(), gameId);

        return players;
    }

    private Player createPlayerFromEntity(PlayerEntity entity) {
        Player player = new Player();
        player.setPlayerId(Optional.empty());
        player.setPlayerName(entity.getPlayerName());
        player.setReady(entity.isReady());
        
        return player;
    }

	public boolean ready(String playerId, String gameId) {
        Optional<PlayerEntity> playerEntity = repro.findByIds(playerId, gameId);

        if (! playerEntity.isPresent()) {
            log.info("Player {} not found for game {}", playerId, gameId);
            return false;
        }

        PlayerEntity entity = playerEntity.get();
        entity.setReady(true);
        repro.save(entity);

        log.info("Player {} is now ready for game {}", playerId, gameId);

        return true;
	}

	public boolean isReady(String playerId, String gameId) {
        Optional<PlayerEntity> player = repro.findByIds(playerId, gameId);
        if (player.isEmpty()) {
            return false;
        }

        return player.get().isReady();
	}
}