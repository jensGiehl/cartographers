package de.agiehl.games.cartographers.card.modell;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {

    private String id;
    private int timeValue;
    private String picture;
    private CardType type;
    private List<TerrainType> possibleTerrainTypes;
    private List<Shape> possibleShapes;
    private Rotation rotation;
    
}