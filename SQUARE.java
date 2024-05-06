public enum SQUARE {
    UNPLAYED("⬛"), // unplayed
    PLAYED("⬜"), // played
    BOMB("💣"), // bombs
    FLAG("⚑"); // flag

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