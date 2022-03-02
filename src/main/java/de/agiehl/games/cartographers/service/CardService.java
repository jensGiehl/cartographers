package de.agiehl.games.cartographers.service;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import de.agiehl.games.cartographers.card.modell.Card;
import de.agiehl.games.cartographers.card.modell.CardType;
import de.agiehl.games.cartographers.card.modell.Shape;
import de.agiehl.games.cartographers.card.modell.TerrainType;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CardService {

    static final int DEFAULT_WIDTH = 42;
    static final int DEFAULT_HEIGHT = 42;

    static final int DEFAULT_COIN_WIDTH = 36;
    static final int DEFAULT_COIN_HEIGHT = 36;

    static final String RUINES_FILENAME = "/static/assets/images/" + CardType.RUINS.getFilename() + ".svg";
    static final String CARD_FILENAME = "/cards/%s/%s.json";

    static final String DEFAULT_MODULE = "base";

    @Cacheable("card")
    public Optional<Card> getCard(@NotNull String module, @NotNull String id) {
        if (StringUtils.isEmpty(module)) {
            module = DEFAULT_MODULE;
        }

        String filename = String.format(CARD_FILENAME, module, id);
        log.info("Try to load card {} from module {} (Filename: {}).", id, module, filename);

        ClassPathResource resource = new ClassPathResource(filename);
        if (resource.exists()) {
            ObjectMapper objMapper = new ObjectMapper();
            try {
                Card card = objMapper.readValue(IOUtils.toString(resource.getInputStream(), UTF_8), Card.class);
                return Optional.ofNullable(card);
            } catch (IOException e) {
                log.error(String.format("Error while reading Card %s from module %s.", id, module), e);
                return Optional.empty();
            }
        }
        
        log.info("Card {} from module {} not found.", id, module);
        return Optional.empty();
    }

    @Cacheable("cardImages")
    public String getCardImageAsSvgString(String module, @NotNull String id, @NotNull int number) throws CardException {
        Card card = getCard(module, id).orElseThrow(() -> new CardException(String.format("Card %s not found in module %s", id, module)));
        
        return createSvgString(card, number);
    }
    
    String createSvgString(@NotNull Card card, @Positive int index)  throws CardException {
        if (card.getType() == CardType.RUINS) {
            return createRuinesSvg();
        }

        List<Shape> possibleShapes = card.getPossibleShapes();
        if (possibleShapes == null || possibleShapes.isEmpty() || possibleShapes.size() < index) {
            throw new CardException(String.format("Invalid position %s for card %s.", index, card.getId()));
        }

        Shape shape = possibleShapes.get(index);
        boolean needToRenderGold = shape.getAmountOfGold() > 0;

        int height = getRows(shape) * DEFAULT_HEIGHT;
        int width = getMaxColumns(shape) * DEFAULT_WIDTH + (needToRenderGold ? DEFAULT_WIDTH : 0);

        StringBuilder str = new StringBuilder();
        str.append(createSvgHeader(width, height));

        int xOffset = needToRenderGold ? DEFAULT_WIDTH : 0;
        int x = xOffset, y = 0;

        for (List<TerrainType> row : shape.getShape()) {
            for (TerrainType terrain : row) {
                if (terrain != TerrainType.BLANK) {
                    str.append(createSvgImageForTerrain(x, y, terrain.name()));
                }
                x+= DEFAULT_WIDTH;
            }
            x=xOffset;
            y+=DEFAULT_HEIGHT;
        }

        str.append( addCoins(shape.getAmountOfGold(), y-DEFAULT_COIN_HEIGHT) );

        str.append("</svg>");

        return str.toString();
    }

    private String addCoins(int amountOfGold, int yPosition) {
        StringBuilder coinSvgString = new StringBuilder();

        int x = 0;
        for (int i=0; i< amountOfGold; i++) {
            coinSvgString.append(createSvgImageForCoin(x, yPosition));
            x += DEFAULT_COIN_WIDTH / 2;
        }

        return coinSvgString.toString();
    }

    private String createRuinesSvg() {
        ClassPathResource resource = new ClassPathResource(RUINES_FILENAME);
        
        try {
            String svgString = new String(Files.readAllBytes(Paths.get(resource.getURI())));
            return svgString;
        } catch (IOException e) {
            throw new CardException(String.format("Error while loading ruins svg (%s).", RUINES_FILENAME), e);
        }
    }

    private String createSvgImageForCoin(int x, int y) {
        return createSvgImage(x, y, DEFAULT_COIN_WIDTH, DEFAULT_COIN_HEIGHT, "coin");
    }

    private String createSvgImageForTerrain(int x, int y, String terrainName) {
        return createSvgImage(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, terrainName);
    }

    private String createSvgImage(int x, int y, int w, int h, String imageName) {
        return String.format("<image x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" xlink:href=\"%s.svg\" />", x, y, w, h, imageName);
    }

    private String createSvgHeader(int width, int height) {
        return String.format("<svg width=\"100%%\" height=\"100%%\" viewBox=\"0 0 %d %d\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">", width, height);
    }

    private int getRows(Shape shape) {
        return shape.getShape().size();
    }

    private Integer getMaxColumns(Shape shape) {
        return shape.getShape().stream().map(List::size).max(Integer::compare).get();
    }

}
