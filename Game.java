import java.util.Scanner;

public class Game {
    private Player player;
    private Board board;
    private Scanner scanner;

    // initialize the scanner here and pass it to needed classes
    public Game() {
        this.scanner = new Scanner(System.in);
    }

    // to startGame
    public void startGame() {
        createNewPlayer(); // create a new player instance
        handleMenuLogic(); // print menu & handle menuLogic
    }

    // to endGame
    public void endGame() {
        scanner.close(); // close the scanner - don't need to update the instance variable
        System.out.println(player.toString());
        System.out.println();
        System.out.println("Thanks for playing - Goodbye");
    }

    // to create a new Board instance & start the game play
    public void createNewBoard() {
        System.out.println("\n========= Main Menu ==========\n" + this.player.getPlayerName()
                + " are you ready to face the challenge. \n\nChoose a board size (suggested amount: 10):");
        int userChoiceSizing = scanner.nextInt();
        scanner.nextLine();
        System.out.println("\nChoose the amount of bombs to set (suggested amount: 10):");
        int userBombAmount = scanner.nextInt();
        scanner.nextLine();
        this.board = new Board(userChoiceSizing, userBombAmount, this.scanner); // pass the scanner to use it in board)
        this.board.boardLogic();
    }

    // create a new Player instance
    public void createNewPlayer() {
        System.out.println();
        System.out.println("Please enter your player name: ");
        String playerName = scanner.nextLine();
        // TODO: add some new player logic here
        this.player = new Player(playerName);
    }

    // to start the main menu
    public void handleMenuLogic() {
        while (this.board.getGameState() == GAME_STATE.IN_PROGRESS) {
            // menu logic
            System.out.println();
            System.out
                    .println(this.player.getPlayerName()
                            + " please make your selection \n\n =========Main Menu==========");

            // loop over enum values to get index eg .ordinal() & message
            for (MAIN_MENU item : MAIN_MENU.values()) {
                System.out.printf("%d) %s \n", item.ordinal() + 1, item.getMenuMessage());
            }

            int menuChoice = scanner.nextInt();
            scanner.nextLine();
            menuLogic(menuChoice);
        }
        endGame();

        if (this.board.getGameState() == GAME_STATE.WON) {
            player.incrGamesWon();
        }

        if (this.board.getGameState() == GAME_STATE.LOST) {
            player.getGamesLost();
        }
    }

    // handle user's menu choice
    public void menuLogic(int userMenuChoice) {
        // get the right index of the menu choice and convert to name
        // ENUM MainM
        MAIN_MENU choice = MAIN_MENU.values()[userMenuChoice - 1];
        switch (choice) {
            case NEW_GAME -> createNewBoard();
            case PLAYER_STATS -> System.out.println(player.toString());
            case NEW_PLAYER -> {
                createNewPlayer();
                // extra messaging for updating
                System.out.println("\n" + this.player.getPlayerName() + " - all updated");
                handleMenuLogic();
            }
            case EXIT -> endGame();
        }
    };

};
