package de.agiehl.games.cartographers.database;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import de.agiehl.games.cartographers.database.entities.PlayerEntity;

public interface PlayerRepository extends CrudRepository<PlayerEntity, Long> {

    @Query("select * from PLAYER where GAME_ID = :gameId order by PLAYER_NAME")
	List<PlayerEntity> findAllByGameId(String gameId);

    @Query("select * from PLAYER where GAME_ID = :gameId and PLAYER_ID = :playerId")
	Optional<PlayerEntity> findByIds(String playerId, String gameId);

}