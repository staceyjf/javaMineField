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
            createNewBoard();
        } else {
            exitGame();
        }
    }

    public void handleGameStats() {
        // check if they have played any games
        System.out.println();
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
                + " are you ready to face the challenge. \n");

        int userChoiceSizing = 0; // run the loop at least once

        // limit board size
        while (userChoiceSizing < 5 || userChoiceSizing > 26) {
            try {
                System.out.println("Choose a board size (min 5 - max 26): ");
                userChoiceSizing = scanner.nextInt();
                if (userChoiceSizing < 5 || userChoiceSizing > 26) {
                    throw new UserErrorException("Please enter a number between 5 to 26.");
                }
                break;
            } catch (InputMismatchException error) {
                System.out.println("Please only use numbers to create a board.");
                scanner.nextLine(); // move this past the error
            } catch (UserErrorException error) {
                System.out.println(error.getMessage()); // use the built in getter
                scanner.nextLine(); // move this past the error
            }
        }

        int userBombAmount = 0;

        // validate userBombAmount
        while (userBombAmount <= 0 || userBombAmount >= (userChoiceSizing * userChoiceSizing)) {
            try {
                System.out.println("\nChoose the amount of bombs to set (suggested amount: 10):");
                userBombAmount = scanner.nextInt();
                if (userBombAmount <= 0 || userBombAmount >= (userChoiceSizing * userChoiceSizing)) {
                    throw new UserErrorException(
                            "Please enter a number greater than 0 and less than the size of the board grid.");
                }
                break;
            } catch (InputMismatchException error) {
                System.out.println(
                        "Number of bombs need to be greater than zero but less than the number of square in the board - please try again:");
                scanner.nextLine(); // move this past the error
                continue;
            } catch (UserErrorException error) {
                System.out.println(error.getMessage()); // use the built in getter
                scanner.nextLine(); // move this past the error
            }
        }

        scanner.nextLine();
        this.board = new Board(userChoiceSizing, userBombAmount); // pass the scanner to use it in board)
        this.board.boardSetUp();
        printDisplayBoard();
        handleGameLogic(); // start game play
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
    }

    public void handleGameLogic() {
        while (this.board.getGameState() == GAME_STATE.IN_PROGRESS) {

            // Flag or reveal
            System.out.println("\nDo you want to flag (F) or reveal(R)?");
            int user_action = 0; // row
            while (true) {
                String userAction = this.scanner.nextLine();
                String updatedUserAction = userAction.replaceAll(" ", "").toLowerCase(); // get a consistent input
                // keep asking until we break out of it with the break
                if (updatedUserAction.equals("f")) {
                    user_action = 0; // 0 for flag
                    break;
                } else if (updatedUserAction.equals("r")) {
                    user_action = 1; // 1 for reveal. Doesn't really matter what this is
                    break;
                } else {
                    System.out.println("Ooops try again - please enter 'F' for flag or 'R' for reveal.");
                }
            }

            // Game co-ordinates
            System.out.println("\nEnter your co-ordinates (eg a1):");
            // co-ords
            int y_coord = 0; // row
            int x_coord = 0; // column

            // check until valid co-ords
            while (true) {
                String userCoords = this.scanner.nextLine(); // ask for new input each time

                // remove any whitespace and lower case
                String updatedCords = userCoords.replaceAll(" ", "").toLowerCase();

                try {
                    // if empty
                    if (updatedCords.isEmpty()) {
                        throw new IllegalArgumentException("Oops - please try again and enter co-ordinates");
                    }

                    // transform "a1" -> split into y,x
                    // column = letter -> to int - 1 (inner) -> y coords
                    // row = int -> -1 for index (outter ) -> x coords
                    char rowChar = updatedCords.charAt(0); // 'a'
                    String columnString = updatedCords.substring(1); // '1'

                    // if not right type for y
                    if (!Character.isLetter(rowChar)) {
                        throw new IllegalArgumentException(
                                "Oops - please try again as the y co-ordinate was not a letter");
                    }

                    // if not the right type for x
                    try {
                        Integer.parseInt(columnString); // will throw an exception if it can't convert
                    } catch (NumberFormatException error) {
                        throw new IllegalArgumentException(
                                "Oops - please try again as the x co-ordinate was not a number");
                    }

                    y_coord = rowChar - 'a'; // subtract "1 the value of a (the unicode value) to get the right index
                    x_coord = Integer.parseInt(columnString) - 1; // to get the right index

                    // if not inside the board
                    if (x_coord < 0 || x_coord >= this.board.getBoardSize() || y_coord < 0
                            || y_coord >= this.board.getBoardSize()) {
                        throw new IllegalArgumentException(
                                "Oops - please try again as the co-ordinates where outside of the board range");
                    }

                    // if played eg 10
                    if (this.board.getLogicBoard()[y_coord][x_coord] == 10) {
                        throw new SquareAlreadyPlayedException(
                                "Oops - that square has already been played. Please try again.");
                    }

                    break; // to exit out when co-ords are valid
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid co-ordinate - please input a co-ord like 'a1'");
                } catch (StringIndexOutOfBoundsException ex) {
                    System.out.println("Invalid co-ordinate - please input a co-ord within the board range");
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                } catch (SquareAlreadyPlayedException ex) {
                    System.out.println(ex.getMessage());
                }

            }

            // handleMove
            this.board.handleMove(y_coord, x_coord, user_action);
            printDisplayBoard();
        }

        // when game state changes, update player stats and end the game
        if (this.board.getGameState() == GAME_STATE.WON) {
            player.incrGamesWon();
        }

        if (this.board.getGameState() == GAME_STATE.LOST) {
            System.out.println("BOOM! You hit a \uD83D\uDCA3");
            System.out
                    .println("     _.-^^---....,,--       \r\n" + //
                            " _--                  --_  \r\n" + //
                            "<                        >)\r\n" + //
                            "|                         | \r\n" + //
                            " \\._                   _./  \r\n" + //
                            "    ```--. . , ; .--'''       \r\n" + //
                            "          | |   |             \r\n" + //
                            "       .-=||  | |=-.   \r\n" + //
                            "       `-=#$%&%$#=-'   \r\n" + //
                            "          | ;  :|     \r\n" + //
                            " _____.,-#%&$@%#&#~,._____");
            System.out.println("");
            player.incrGamesLost();
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

    public void printDisplayBoard() {
        // print welcome message and board
        System.out.println();
        System.out.println("================== GAME STATS ================");
        System.out.printf("Moves to win: %d", this.board.getNumberOfMovesToWin());
        System.out.println();
        System.out.println();
        System.out.println("================== MY BOARD ==================\n");

        // board
        // number header
        System.out.print("   ");
        for (int i = 1; i <= this.board.getBoardSize(); i++) {
            System.out.printf("%3d ", i); // print the number columns with a fixed width of 3 characters
        }
        System.out.println();

        // Print top border
        System.out.print("   +");
        for (int j = 0; j < this.board.getBoardSize(); j++) {
            System.out.print("---+");
        }
        System.out.println();

        for (int i = 0; i < this.board.getDisplayBoard().length; i++) {
            // letter column
            System.out.printf("%2c |", (char) (i + 'A'));
            for (int j = 0; j < this.board.getDisplayBoard()[i].length; j++) {
                // squares
                System.out.printf(" %s |", this.board.getDisplayBoard()[i][j]);
            }
            System.out.println();

            // Print row separator
            if (i < this.board.getDisplayBoard().length) {
                System.out.print("   +");
                for (int j = 0; j < this.board.getBoardSize(); j++) {
                    System.out.print("---+");
                }
                System.out.println();
            }
        }
    }

};
