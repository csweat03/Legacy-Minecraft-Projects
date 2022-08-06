package me.xx.api.cheat;

import org.apache.commons.lang3.text.WordUtils;

/**
 * @author Kyle
 * @since 3/20/2018
 **/
public enum Category {
    COMBAT, MOTION, VISUAL, PLAYER, MISC, GHOST;

    @Override
    public String toString() {
        return WordUtils.capitalizeFully(super.toString());
    }
}