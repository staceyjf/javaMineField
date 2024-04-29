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

    // to start the main menu
    public void startGame() {
        this.messaging = "======== welcome to Javafield ======= \n";
        System.out.println(messaging);

        // create new player
        createNewPlayer();

        // menu logic
        this.messaging = " please make your selection \n\n =========Main Menu==========";
        System.out.println();
        System.out.println(this.player.getPlayerName() + this.messaging);

        // loop over enum values to get index eg .ordinal() & message
        for (MAIN_MENU item : MAIN_MENU.values()) {
            System.out.printf("%d) %s \n", item.ordinal() + 1, item.getMenuMessage());
        }
        int menuChoice = scanner.nextInt();
        menu(menuChoice);
    }

    // to create a new Board instance
    public void createNewBoard() {
        this.messaging = "\n========= Main Menu========== \nAre you ready to face the challenge. \n\nChoose a board size (max 15)";
        System.out.println("\n" + this.player.getPlayerName() + this.messaging);
        int userChoiceSizing = scanner.nextInt();
        this.board = new Board(userChoiceSizing);
        this.board.printBoard();
    }

    public void createNewPlayer() {
        this.messaging = "Please enter your player name: ";
        System.out.println(this.messaging);
        String playerName = scanner.nextLine();
        // TODO: add some new player logic here
        this.player = new Player(playerName);
    }

    public void menu(int userMenuChoice) {
        // get the right index of the menu choice and convert to name
        // ENUM MainM
        MAIN_MENU choice = MAIN_MENU.values()[userMenuChoice - 1];
        switch (choice) {
            case NEW_GAME -> createNewBoard();
            case PLAYER_STATS -> {
                /* function to print scores */}
            case NEW_PLAYER -> createNewPlayer();
        }
    }
}
