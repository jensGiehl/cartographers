package de.agiehl.games.cartographers.card.modell;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum CardType {
    @JsonEnumDefaultValue NORMAL,
    RUINS,
    AMBUSH;

    public String getFilename() {
        return name().toLowerCase();
    }
}
