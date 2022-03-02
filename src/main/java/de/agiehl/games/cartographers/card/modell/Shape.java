package de.agiehl.games.cartographers.card.modell;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shape {
    private int amountOfGold;
    private List<List<TerrainType>> shape;

    public void addRow(TerrainType... types) {
        if (shape == null) {
            shape = new ArrayList<>();
        }

        shape.add(asList(types));
    }

    public void toShapeForm(char[][] shape) {
        List<List<TerrainType>> holeShape = new ArrayList<List<TerrainType>>();
        for (char[] row : shape) {
            List<TerrainType> columns = new ArrayList<>();
            for (char col : row) {
                columns.add(TerrainType.findByCharacter(col));
            }
            holeShape.add(columns);
        }

        this.shape = holeShape;
    }
}
