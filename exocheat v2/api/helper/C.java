package club.shmoke.main.api.helper;

public enum C {

    BLACK("0"),
    DARKBLUE("1"),
    DARKGREEN("2"),
    DARKAQUA("3"),
    DARKRED("4"),
    DARKPURPLE("5"),
    GOLD("6"),
    GRAY("7"),
    DRAYGRAY("8"),
    INDIGO("9"),
    GREEN("A"),
    AQUA("B"),
    RED("C"),
    PINK("D"),
    YELLOW("E"),
    WHITE("F"),
    STRIKE("M"),
    UNDERLINE("N"),
    BOLD("L"),
    RANDOM("K"),
    ITALIC("O"),
    RESET("R");

    private String code;

    C(String code) {
        this.code = "\247" + code;
    }

    public String getCode() {
        return code;
    }

}
