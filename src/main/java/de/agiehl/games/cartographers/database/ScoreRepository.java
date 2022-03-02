package de.agiehl.games.cartographers.database;

import org.springframework.data.repository.CrudRepository;

import de.agiehl.games.cartographers.database.entities.ScoreEntity;

public interface ScoreRepository extends CrudRepository<ScoreEntity, Long> {

}