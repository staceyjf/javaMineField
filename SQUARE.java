public enum SQUARE {
    UNPLAYED("\u2B1B"), // unplayed
    PLAYED("\u2B1C"), // played
    BOMB("x"); // bomb

    private String displayValue; // to store the display

    // when you call an ENUM it uses this constructor to create an instance / can't
    // call this directly
    SQUARE(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return this.displayValue;
    }
}