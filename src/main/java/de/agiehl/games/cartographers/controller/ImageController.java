package de.agiehl.games.cartographers.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import de.agiehl.games.cartographers.service.CardException;
import de.agiehl.games.cartographers.service.SvgProviderService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
/**
 * This is a hack. I can't manage to render SVG correctly without
 * a wrapper around them. So this controller wraps the SVG graphics.
 */
public class ImageController {

    private final SvgProviderService service;

    @GetMapping(path = "assets/images/img_{name:[a-zA-Z]*}.svg", produces = "image/svg+xml")
    public @ResponseBody String getCardImage(
            @PathVariable(name="name", required = true) String imageName, 
            HttpServletResponse response) {
                try {
                    return service.getImageAsSvgString(imageName.toLowerCase());
                } catch (CardException e) {
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    return null;
                }
    }
    
}