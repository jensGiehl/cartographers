package de.agiehl.games.cartographers.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import de.agiehl.games.cartographers.card.modell.Card;
import de.agiehl.games.cartographers.service.CardException;
import de.agiehl.games.cartographers.service.CardService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping(path = "assets/images/card_{module:[a-zA-Z]*}_{id:[0-9]*}-{number:[0-9]*}.svg", produces = "image/svg+xml")
    public @ResponseBody String getCardImage(
            @PathVariable(name="module", required = false) String module, 
            @PathVariable(name = "id", required = true) String id,
            @PathVariable(name="number", required = true) int number,
            HttpServletResponse response) {
                try {
                    return cardService.getCardImageAsSvgString(module, id, number);
                } catch (CardException e) {
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    return null;
                }
    }

    @GetMapping(path={"{module}/card/{id}", "card/{id}" })
    public @ResponseBody Card getCard(
        @PathVariable(name="module", required= false) String module,
        @PathVariable(name="id", required=true) String id,
        HttpServletResponse response) {
            Optional<Card> card = cardService.getCard(module, id);

            if (! card.isPresent()) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }

            return card.get();
    }

    @GetMapping(path="/showCard/{module:[a-zA-Z]*}/{id:[0-9]*}")
    public String testCard(
        @PathVariable(name="module", required = true) String module, 
        @PathVariable(name = "id", required = true) String id,
        Model model) {
        Optional<Card> card = cardService.getCard(module, id);
        model.addAttribute("card", card.orElseThrow());
        model.addAttribute("module", module);

        return "showCard";
    }
    
}