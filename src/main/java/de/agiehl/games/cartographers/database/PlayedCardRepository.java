package de.agiehl.games.cartographers.database;

import org.springframework.data.repository.CrudRepository;

import de.agiehl.games.cartographers.database.entities.PlayedCardEntity;

public interface PlayedCardRepository extends CrudRepository<PlayedCardEntity, Long>{

}