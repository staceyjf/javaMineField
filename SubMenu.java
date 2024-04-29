public enum SubMenu {
    NEW_GAME(1, "Ready to play a new Game"),
    PLAYER_STATS(2, "Display game statistics");

    private String menuMessage;
    private int menuNumber;

    private SubMenu(int menuNumber, String menuMessage) {
        this.menuNumber = menuNumber;
        this.menuMessage = menuMessage;
    }

    // getter methods
    public int getMenuNumber() {
        return this.menuNumber;
    }

    public String getMenuMessage() {
        return this.menuMessage;
    }

}
