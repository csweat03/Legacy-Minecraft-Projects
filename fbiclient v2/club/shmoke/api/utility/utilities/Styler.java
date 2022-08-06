package club.shmoke.api.utility.utilities;

import org.apache.commons.lang3.text.WordUtils;

/**
 * @author Christian
 */
public class Styler {

    private static Case defaultCase = Case.LOWER;

    public static String switchCase(String string) {
        String temp = string;
        switch (defaultCase) {
            case UPPER:
                temp = string.toUpperCase();
                break;
            case LOWER:
                temp = string.toLowerCase();
                break;
            case NORMAL:
                temp = WordUtils.capitalizeFully(string);
                break;
        }
        return temp;
    }

    private enum Case {
        UPPER, NORMAL, LOWER
    }

}
