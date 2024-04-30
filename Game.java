import java.util.Scanner;

public class Game {
    private Player player;
    private Board board;
    private Scanner scanner;
    private String messaging;

    //
    public Game() {
        this.scanner = new Scanner(System.in);
    }

    // to startGame
    public void startGame() {
        createNewPlayer(); // create a new player instance
        handleMenuLogic(); // print menu & handle menuLogic
    }

    // to end game
    public void endGame() {
        scanner.close(); // close the scanner - don't need to update the instance variable
        this.messaging = "Thanks for playing - Goodbye";
        System.out.println();
        System.out.println(this.messaging);
    }

    // to start the main menu
    public void handleMenuLogic() {
        // menu logic
        this.messaging = " please make your selection \n\n =========Main Menu==========";
        System.out.println();
        System.out.println(this.player.getPlayerName() + this.messaging);

        // loop over enum values to get index eg .ordinal() & message
        for (MAIN_MENU item : MAIN_MENU.values()) {
            System.out.printf("%d) %s \n", item.ordinal() + 1, item.getMenuMessage());
        }

        int menuChoice = scanner.nextInt();
        scanner.nextLine();
        menuLogic(menuChoice);
    }

    // to create a new Board instance
    public void createNewBoard() {
        this.messaging = "\n========= Main Menu========== \nAre you ready to face the challenge. \n\nChoose a board size (max 15)";
        System.out.println("\n" + this.player.getPlayerName() + this.messaging);
        int userChoiceSizing = scanner.nextInt();
        this.board = new Board(userChoiceSizing);
        this.board.printBoard();
    }

    // create a new Player instance
    public void createNewPlayer() {
        System.out.println();
        this.messaging = "Please enter your player name: ";
        System.out.println(this.messaging);
        String playerName = scanner.nextLine();
        // TODO: add some new player logic here
        this.player = new Player(playerName);
    }

    // to updated the player name
    public void updateNewPlayer() {
        createNewPlayer();
        this.messaging = " - all updated";
        System.out.println("\n" + this.player.getPlayerName() + this.messaging);
        handleMenuLogic();
    }

    // handle user's menu choice
    public void menuLogic(int userMenuChoice) {
        // get the right index of the menu choice and convert to name
        // ENUM MainM
        MAIN_MENU choice = MAIN_MENU.values()[userMenuChoice - 1];
        switch (choice) {
            case NEW_GAME -> createNewBoard();
            case PLAYER_STATS -> {
                /* function to print scores */}
            case NEW_PLAYER -> updateNewPlayer();
            case EXIT -> endGame();
        }
    }
}
