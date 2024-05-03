import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    private Player player;
    private Board board;
    private Scanner scanner;

    // initialize the scanner here and pass it to needed classes
    public Game() {
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        createNewPlayer(); // create a new player instance
        handleMenuLogic(); // print menu & handle menuLogic
    }

    public void endGame() {
        System.out.println(player.toString()); // print player stats
        System.out.println();
        System.out.println("Keen to play again? - y/n ");
        String userChoice = scanner.nextLine();

        // if they want another game (might have to change this)
        if (userChoice.toLowerCase().equals("y")) {
            handleMenuLogic();
        } else {
            exitGame();
        }
    }

    public void handleGameStats() {
        // check if they have played any games
        if ((this.player.getGamesLost() + this.player.getGamesWon()) == 0) {
            System.out.println("\n You haven't played any games yet!");
        } else {
            System.out.println(player.toString());
        }
        handleMenuLogic();
    }

    public void exitGame() {
        scanner.close(); // close the scanner - don't need to update the instance variable
        System.out.println();
        System.out.println("Thanks for playing - Goodbye");
        System.exit(0);
    }

    // to create a new Board instance & start the game play
    public void createNewBoard() {
        System.out.println("\n========= Main Menu ==========\n" + this.player.getPlayerName()
                + " are you ready to face the challenge. \n\nChoose a board size (min 5 - max 26):");

        int userChoiceSizing = 0; // run the loop at least once

        // limit board size
        while (userChoiceSizing < 5 || userChoiceSizing > 26) {
            try {
                userChoiceSizing = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException error) {
                System.out.println("Board grids need to be between 5 to 26:");
                scanner.nextLine(); // move this past the error
            }
        }

        System.out.println("\nChoose the amount of bombs to set (suggested amount: 10):");
        int userBombAmount = 0;

        // validate userBombAmount
        while (userBombAmount <= 0 || userBombAmount == userChoiceSizing) {
            try {
                userBombAmount = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException error) {
                System.out.println(
                        "Number of bombs need to be greater than zero but less than the number of square in the board - please try again:");
                scanner.nextLine(); // move this past the error
            }
        }

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
        // menu logic
        System.out.println();
        System.out.println("=========Main Menu==========");
        System.out.println();

        // loop over enum values to get index eg .ordinal() & message
        for (MAIN_MENU item : MAIN_MENU.values()) {
            System.out.printf("%d) %s \n", item.ordinal() + 1, item.getMenuMessage());
        }

        int menuChoice = scanner.nextInt();
        scanner.nextLine();
        menuLogic(menuChoice);

        // only check if the board has been created
        if (this.board != null) {
            if (this.board.getGameState() == GAME_STATE.WON) {
                player.incrGamesWon();
            }

            if (this.board.getGameState() == GAME_STATE.LOST) {
                player.getGamesLost();
            }
        }

        endGame();
    }

    // handle user's menu choice
    public void menuLogic(int userMenuChoice) {
        // get the right index of the menu choice and convert to name
        // ENUM MainM
        MAIN_MENU choice = MAIN_MENU.values()[userMenuChoice - 1];
        switch (choice) {
            case NEW_GAME -> createNewBoard();
            case PLAYER_STATS -> handleGameStats();
            case NEW_PLAYER -> {
                createNewPlayer();
                System.out.println("\n" + this.player.getPlayerName() + " - all updated");
                handleMenuLogic();
            }
            case EXIT -> exitGame();
        }
    };

};
