package de.agiehl.games.cartographers.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.agiehl.games.cartographers.service.GameService;
import de.agiehl.games.cartographers.service.PlayerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@AllArgsConstructor
@Slf4j
public class GameController {

    private GameService gameService;

    private PlayerService playerService;

    @GetMapping(path="/game/{gameId}/start")
    public String startGame(@PathVariable(name = "gameId") String gameId,
                HttpServletRequest request, Model model) {
                    Cookie[] cookies = request.getCookies();
                    String adminToken = null;
                    for (Cookie c : cookies) {
                        if (c.getName().equals(gameId)) {
                            adminToken = c.getValue();
                        }
                    }

                    gameService.startGame(gameId);

        return null;
    }

    @GetMapping(path = "/game/{gameId}/player/{playerId}")
    public String game(@PathVariable(name = "gameId") String gameId, @PathVariable(name = "playerId") String playerId,
            HttpServletRequest request, Model model) {
        
        model.addAttribute("playerList", playerService.listAllPlayers(gameId));

        boolean myStatus = playerService.isReady(playerId, gameId);
        model.addAttribute("myStatus", myStatus);

        model.addAttribute("gameId", gameId);

        return "prepareGame";
    }

    @PostMapping(path = "/game/{gameId}/player/{playerId}/ready")
    @ResponseBody
    public ResponseEntity<String> postMethodName(@PathVariable(name = "gameId") String gameId, @PathVariable(name = "playerId") String playerId) {
        boolean success = playerService.ready(playerId, gameId);

        if (success) {
            return new ResponseEntity<String>("ok",HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("nok", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="/game/{gameId}/player/{playerId}/checkReady")
    public String checkStatus(@PathVariable(name = "gameId") String gameId, @PathVariable(name = "playerId") String playerId, Model model) {
        
        model.addAttribute("playerList", playerService.listAllPlayers(gameId));

        boolean myStatus = playerService.isReady(playerId, gameId);
        model.addAttribute("myStatus", myStatus);

        return "prepareGame :: playerList";
    }
    

}