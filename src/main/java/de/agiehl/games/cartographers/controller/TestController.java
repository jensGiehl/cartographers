package de.agiehl.games.cartographers.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import de.agiehl.games.cartographers.card.modell.Card;
import de.agiehl.games.cartographers.service.CardService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final CardService cardService;

    @GetMapping(path="/test")
    public String test(Model model) {
        String module="base";

        Optional<Card> card = cardService.getCard(module, "01");
        model.addAttribute("card", card.orElseThrow());
        model.addAttribute("module", module);

        Optional<Card> card2 = cardService.getCard(module, "06");
        model.addAttribute("card2", card2.orElseThrow());

        Optional<Card> card3 = cardService.getCard(module, "13");
        model.addAttribute("card3", card3.orElseThrow());

        Optional<Card> card4 = cardService.getCard(module, "17");
        model.addAttribute("card4", card4.orElseThrow());

        return "test";
    }
    
}