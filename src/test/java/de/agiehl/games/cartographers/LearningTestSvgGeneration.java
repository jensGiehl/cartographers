package de.agiehl.games.cartographers;

import static de.agiehl.games.cartographers.card.modell.TerrainType.B;
import static de.agiehl.games.cartographers.card.modell.TerrainType.BLANK;
import static de.agiehl.games.cartographers.card.modell.TerrainType.F;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.agiehl.games.cartographers.card.modell.Shape;
import de.agiehl.games.cartographers.card.modell.TerrainType;

public class LearningTestSvgGeneration {

    @Test
    public void createSvg() {
        Shape shape = new Shape();
        shape.addRow(TerrainType.EMPTY, TerrainType.FARM, TerrainType.FOREST, TerrainType.MONSTER, TerrainType.MOUNTAIN, TerrainType.VILLAGE, TerrainType.WATER);
        shape.addRow(B, F, B);
        shape.addRow(B, F, B);
        shape.addRow(F, B, F);

        int h = shape.getShape().size() * 42;
        int w = shape.getShape().stream().map(List::size).max(Integer::compare).get() * 42;

        StringBuilder str = new StringBuilder();
        str.append("<svg width=\"100%\" height=\"100%\" viewBox=\"0 0 " + w + " " + h
                + "\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
        int y = 0;
        int x = 0;
        for (List<TerrainType> row : shape.getShape()) {
            for (TerrainType t : row) {
                if (t != BLANK) {
                    str.append("<image x=\""+x+"\" y=\""+y+"\" width=\"42\" height=\"42\" xlink:href=\""+t.name().toLowerCase()+".svg\" />");
                }
                x+= 42;
            }
            x=0;
            y+=42;
        }

        str.append("</svg>");

        System.out.println(str.toString());
    }

}