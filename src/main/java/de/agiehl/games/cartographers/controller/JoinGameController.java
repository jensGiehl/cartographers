package de.agiehl.games.cartographers.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.agiehl.games.cartographers.service.GameService;
import de.agiehl.games.cartographers.service.PlayerService;
import de.agiehl.games.cartographers.service.dto.Player;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@Slf4j
public class JoinGameController {

    private PlayerService playerService;

    private GameService gameService;

    @PostMapping(path="/joinGame")
    public String joinGame(@ModelAttribute(name="gameId") String gameId, @ModelAttribute(name = "name") String playerName, Locale locale,
    HttpServletResponse response) {
        Player createdPlayer = playerService.createPlayer(playerName, gameId);

        return "redirect:/game/"+ gameId + "/player/" + createdPlayer.getPlayerId().orElseThrow() + "/";
    }

}