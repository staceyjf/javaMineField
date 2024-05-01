import java.util.Scanner;

public class Game {
    private Player player;
    private Board board;
    private Scanner scanner;
    private String messaging;

    // initialize the scanner here and pass it to needed classes
    public Game() {
        this.scanner = new Scanner(System.in);
    }

    // to startGame
    public void startGame() {
        createNewPlayer(); // create a new player instance
        handleMenuLogic(); // print menu & handle menuLogic
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

    // handle user's menu choice
    public void menuLogic(int userMenuChoice) {
        // get the right index of the menu choice and convert to name
        // ENUM MainM
        MAIN_MENU choice = MAIN_MENU.values()[userMenuChoice - 1];
        switch (choice) {
            case NEW_GAME -> createNewBoard();
            case PLAYER_STATS -> printPlayerScores();
            case NEW_PLAYER -> {
                createNewPlayer();
                // extra messaging for updating
                this.messaging = " - all updated";
                System.out.println("\n" + this.player.getPlayerName() + this.messaging);
                handleMenuLogic();
            }
            case EXIT -> {
                scanner.close(); // close the scanner - don't need to update the instance variable
                this.messaging = "Thanks for playing - Goodbye";
                System.out.println();
                System.out.println(this.messaging);
            }
        }
    };

    // to create a new Board instance & start the game play
    public void createNewBoard() {
        this.messaging = " are you ready to face the challenge. \n\nChoose a board size (max 15)";
        System.out.println("\n========= Main Menu ==========\n" + this.player.getPlayerName() + this.messaging);
        int userChoiceSizing = scanner.nextInt();
        this.board = new Board(userChoiceSizing, this.scanner); // pass the scanner to use it in board)
        this.board.boardLogic();
    }

    public void printPlayerScores() {
        this.messaging = "Print something here TODO";
        System.out.println(this.messaging);
    }
};
