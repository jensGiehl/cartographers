package de.agiehl.games.cartographers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.agiehl.games.cartographers.service.GameService;
import de.agiehl.games.cartographers.service.dto.GameCounter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class GameRestController {

    private GameService gameService;

    @GetMapping(path = "/game")
    public GameCounter countGames() {
        return gameService.countGames();
    }

}