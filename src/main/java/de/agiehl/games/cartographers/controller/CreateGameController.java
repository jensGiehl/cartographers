package de.agiehl.games.cartographers.controller;

import static de.agiehl.games.cartographers.controller.CookieManager.PLAYER_NAME_COOKIE_NAME;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.agiehl.games.cartographers.service.GameService;
import de.agiehl.games.cartographers.service.PlayerService;
import de.agiehl.games.cartographers.service.dto.Game;
import de.agiehl.games.cartographers.service.dto.Player;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@Slf4j
public class CreateGameController {

    private MessageSource messageSource;

    private GameService gameService;

    private PlayerService playerService;

    @PostMapping(value = "createGame")
    public String createGame(@ModelAttribute(name = "name") String playerName, Locale locale,
            HttpServletResponse response) {
        if (playerName.isBlank()) {
            playerName = messageSource.getMessage("guest.name.prefix", new Object[] { randomNumeric(4) }, locale);
            log.info("Guestname was generated: {}", playerName);
        }

        Game createdGame = gameService.createGame();
        Player createdPlayer = playerService.createPlayer(playerName, createdGame.getGameId());

        Cookie gameAdminCookie = new Cookie(createdGame.getGameId(), createdGame.getAdminToken().orElseThrow());
        gameAdminCookie.setMaxAge(Integer.MAX_VALUE);

        Cookie playerNameCookie = new Cookie(PLAYER_NAME_COOKIE_NAME, playerName);
        playerNameCookie.setMaxAge(Integer.MAX_VALUE);
        
        response.addCookie(gameAdminCookie);
        response.addCookie(playerNameCookie);

        return "redirect:/game/"+ createdGame.getGameId() + "/player/" + createdPlayer.getPlayerId().orElseThrow() + "/";
    }

}