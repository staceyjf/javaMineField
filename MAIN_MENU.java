public enum MAIN_MENU {
    NEW_GAME("Play a new game"),
    PLAYER_STATS("Display game statistics"),
    NEW_PLAYER("New Player"),
    EXIT("Exit");

    private String menuMessage;

    // when you call a ENUM it uses this constructor to create an instance / can't
    // call this directly
    private MAIN_MENU(String menuMessage) {
        this.menuMessage = menuMessage;
    }

    // getter methods
    public String getMenuMessage() {
        return this.menuMessage;
    }
}
