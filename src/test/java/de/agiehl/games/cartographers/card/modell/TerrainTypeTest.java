package de.agiehl.games.cartographers.card.modell;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class TerrainTypeTest {

    @Test
    public void characterShouldBeUnique() {
        assertTrue(asList(TerrainType.values())
        .stream().map(TerrainType::getCharacter)
        .allMatch(new HashSet<>()::add), "Characters not unique");
    }
    
}