package de.agiehl.games.cartographers.database;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import de.agiehl.games.cartographers.database.entities.GameEntity;

public interface GameRepository extends CrudRepository<GameEntity, Long> {

    @Query("select count(id) from GAME where GAME_STATUS = :statusCode")
    long countWithStatus(short statusCode);
    
    @Query("select * from GAME where gameId = :gameId")
    Optional<GameEntity> findGameByGameId(String gameId);

}