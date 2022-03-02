package de.agiehl.games.cartographers;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static de.agiehl.games.cartographers.card.modell.CardType.NORMAL;
import static de.agiehl.games.cartographers.card.modell.TerrainType.FARM;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import de.agiehl.games.cartographers.card.modell.Card;
import de.agiehl.games.cartographers.card.modell.Shape;

public class LearningTestJsonGeneration {

    @Test
    public void generateCardAsJson() throws JsonProcessingException {
        Card card = new Card();
        card.setId("08");
        card.setPicture(null);
        card.setTimeValue(1);
        card.setType(NORMAL);
        card.setPossibleTerrainTypes(singletonList(FARM));

        Shape shape1 = new Shape();
        shape1.setAmountOfGold(1);
        shape1.toShapeForm(new char[][] { //
                { '#' }, //
                { '#' } //
        });

        Shape shape2 = new Shape();
        shape2.setAmountOfGold(0);
        shape2.toShapeForm(new char[][] { //
                { '-', '#', '-' }, //
                { '#', '#', '#' }, //
                { '-', '#', '-' }, //
        });

        card.setPossibleShapes(asList(shape1, shape2));

        ObjectMapper objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
        String jsonValue = objectMapper.writeValueAsString(card);
        
        System.out.println(jsonValue);
    }

}