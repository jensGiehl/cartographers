package de.agiehl.games.cartographers.controller;

import static de.agiehl.games.cartographers.controller.CookieManager.PLAYER_NAME_COOKIE_NAME;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@Slf4j
public class IndexController {

    @GetMapping(path = { "/", "index" })
    public String index(@CookieValue(name = PLAYER_NAME_COOKIE_NAME, required = false) String playerName, Model model) {
        model.addAttribute("playerName", playerName);
        return "index";
    }

}