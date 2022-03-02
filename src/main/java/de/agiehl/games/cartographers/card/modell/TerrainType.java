package de.agiehl.games.cartographers.card.modell;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TerrainType {
    FOREST('O'),
    VILLAGE('V'),
    FARM('F'),
    WATER('W'),
    MONSTER('X'),
    MOUNTAIN('M'),
    @JsonEnumDefaultValue BLANK('-'),
    EMPTY('*'),
    FILLED('#');

    public static TerrainType B = BLANK;
    public static TerrainType E = EMPTY;
    public static TerrainType F = FILLED;

    @JsonValue
    private final char character;

	public static TerrainType findByCharacter(char col) {
        for (TerrainType type : TerrainType.values()) {
            if (type.getCharacter() == col) {
                return type;
            }
        }
		return EMPTY;
    }
    
    public String getFilename() {
        return name().toLowerCase();
    }
    
}