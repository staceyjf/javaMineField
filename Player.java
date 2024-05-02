public class Player {
    private String playerName;
    private int gamesWon;
    private int gamesLost;

    public Player(String playerName) {
        this.playerName = playerName;
        this.gamesWon = 0;
        this.gamesLost = 0;
    }

    // toString Override
    @Override
    public String toString() {
        return this.playerName
                + ": you have played: "
                + (this.gamesWon + this.gamesLost)
                + "\n" + "won: " + this.gamesWon
                + "\n" + "lost: " + this.gamesLost;
    }

    // getters
    public String getPlayerName() {
        return this.playerName;
    }

    public int getGamesWon() {
        return this.gamesWon;
    }

    public int getGamesLost() {
        return this.gamesLost;
    }

    // setters
    public int incrGamesWon() {
        return gamesWon++;
    }

    public int incrGamesLost() {
        return gamesLost++;
    }

}
