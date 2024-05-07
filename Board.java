
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class Board {
    private int[][] logicBoard;
    private String[][] displayBoard;
    private int boardSize;
    private int totalBombs;
    private int totalSurroundingBombs;
    private int numberOfMovesToWin;
    private Scanner scanner;
    private GAME_STATE gameState; // controls the game state to update Game

    public Board(int boardSize, int totalBombs, Scanner scanner) {
        this.boardSize = boardSize; // user defined
        this.totalBombs = totalBombs; // user defined
        this.totalSurroundingBombs = 0;
        this.numberOfMovesToWin = ((boardSize * boardSize) - totalBombs);
        this.scanner = scanner;
        this.logicBoard = new int[boardSize][boardSize]; // initialise to zero as default
        this.displayBoard = new String[boardSize][boardSize];
        this.gameState = GAME_STATE.IN_PROGRESS;

        // initialize board with unplayed squares
        for (int i = 0; i < this.displayBoard.length; i++) {
            for (int j = 0; j < this.displayBoard[i].length; j++) {
                this.displayBoard[i][j] = SQUARE.UNPLAYED.getDisplayValue();
            }
        }
    }

    public void boardLogic() {
        // set the bombs
        int amountOfBombsSet = 0;
        Random random = new Random();

        // plant the bombs
        while (amountOfBombsSet != totalBombs) {
            int random_row = random.nextInt(logicBoard.length);
            int random_col = random.nextInt(logicBoard.length);
            // if the square does not have a bomb
            if (logicBoard[random_row][random_col] != 9) {
                // set it as a bomb
                logicBoard[random_row][random_col] = 9;
                // increase bomb count
                amountOfBombsSet++;
            }
        }

        printDisplayBoard(); // prints the board

        // continually ask for new squares until game is either won or lost
        while (this.gameState == GAME_STATE.IN_PROGRESS) {
            int[] validCoords = checkCoords();
            handleGamePlay(validCoords);
            // print board
            if (this.gameState != GAME_STATE.LOST) {
                printDisplayBoard();
            }
        }
    }

    public int[] checkCoords() {

        System.out.println("\nDo you want to flag (F) or reveal(R)?");

        // flag logic
        int user_action = 0; // row

        // keep asking until we break out of it with the break
        while (true) {
            String userAction = this.scanner.nextLine();
            String updatedUserAction = userAction.replaceAll(" ", "").toLowerCase(); // get a consistent input

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
                    throw new IllegalArgumentException("Oops - please try again as the y co-ordinate was not a letter");
                }

                // if not the right type for x
                try {
                    Integer.parseInt(columnString); // will throw an exception if it can't convert
                } catch (NumberFormatException error) {
                    throw new IllegalArgumentException("Oops - please try again as the x co-ordinate was not a number");
                }

                y_coord = rowChar - 'a'; // subtract "1 the value of a (the unicode value) to get the right index
                x_coord = Integer.parseInt(columnString) - 1; // to get the right index

                // if not inside the board
                if (x_coord < 0 || x_coord >= boardSize || y_coord < 0
                        || y_coord >= boardSize) {
                    throw new IllegalArgumentException(
                            "Oops - please try again as the co-ordinates where outside of the board range");
                }

                // if played eg 1
                if (logicBoard[y_coord][x_coord] == 1) {
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

        // return validate co-ordiates
        return new int[] { y_coord, x_coord, user_action };
    }

    // this check if its a flag, the game is won, lost or in progress
    public void handleGamePlay(int[] validCoords) {
        int y_coord = validCoords[0];
        int x_coord = validCoords[1];
        int user_action = validCoords[2];

        // if the user_action is 0 which is flag
        if (user_action == 0) {
            this.displayBoard[y_coord][x_coord] = this.displayBoard[y_coord][x_coord]
                    .equals(SQUARE.FLAG.getDisplayValue()) // if its already a flag
                            ? SQUARE.UNPLAYED.getDisplayValue() // toggle to unplayed
                            : SQUARE.FLAG.getDisplayValue(); // mark as a flag
            return; // exit here so we skip the rest of the logic for reveal
        }

        // check to see if game is lost or square is unplayed
        switch (logicBoard[y_coord][x_coord]) {
            case 9: // if a bomb - end game
                this.displayBoard[y_coord][x_coord] = SQUARE.BOMB.getDisplayValue(); // updated the display board
                System.out.println("BOOM! You hit a \uD83D\uDCA3");
                // how would i update the player's stats if connected board
                this.gameState = GAME_STATE.LOST;
                break;
            case 0: // if unplayed - check if game is won or game must continue
                cascadeSquares(y_coord, x_coord);
                // I've already handled case 1 in the while loop in checkCoords
        }
    }

    // handle the cascading squares
    public void cascadeSquares(int y_coord, int x_coord) {
        this.totalSurroundingBombs = 0; // reset the bomb counter

        // create an exit point for the recursion eg if the square is a bomb (9) or been
        // played (1) or has a flag eg it will go back to asking for valid coords (game
        // logic)
        if (this.logicBoard[y_coord][x_coord] == 9 || this.logicBoard[y_coord][x_coord] == 1
                || this.displayBoard[y_coord][x_coord].equals(SQUARE.FLAG.getDisplayValue())) {
            return;
        }

        // all the positions to check
        List<int[]> transformations = Arrays.asList(
                new int[] { -1, -1 },
                new int[] { -1, 0 },
                new int[] { -1, 1 },
                new int[] { 0, -1 },
                new int[] { 0, 1 },
                new int[] { 1, -1 },
                new int[] { 1, 0 },
                new int[] { 1, 1 });

        // logic to check if adjacent are bombs. If so, exit here and ask for valid
        // coords
        for (int[] transformation : transformations) {
            int newX = x_coord + transformation[1];
            int newY = y_coord + transformation[0];

            // ensure co-ords are valid
            if (newY >= 0 && newY < logicBoard.length && newX >= 0 && newX < logicBoard[0].length) {
                if (this.logicBoard[newY][newX] == 9) {
                    // if the square is adjacent to a bomb, reveal it and stop the cascade
                    this.logicBoard[y_coord][x_coord] = 1; // updated to play
                    this.displayBoard[y_coord][x_coord] = SQUARE.PLAYED.getDisplayValue(); // updated the display board
                                                                                           // to played
                    this.numberOfMovesToWin--; // reduce the number of moves left
                    this.totalSurroundingBombs++; // update the bomb count
                    return;
                }
            }
        }

        // if the square is not adjacent to any bombs, reveal it and continue the
        // cascade
        this.logicBoard[y_coord][x_coord] = 1; // updated to play
        this.displayBoard[y_coord][x_coord] = SQUARE.PLAYED.getDisplayValue(); // updated the display board with
        this.numberOfMovesToWin--; // reduce the number of moves left

        // cascade the squares
        for (int[] transformation : transformations) {
            int newX = x_coord + transformation[1];
            int newY = y_coord + transformation[0];

            // ensure co-ords are valid
            if (newY >= 0 && newY < logicBoard.length && newX >= 0 && newX < logicBoard[0].length) {
                if (this.logicBoard[newY][newX] == 0) {
                    cascadeSquares(newY, newX); // if its not played (0), call the function until it reaches
                                                // the exit clause
                }
            }
        }
        // check to see if game is won
        if (numberOfMovesToWin == 0)

        {
            this.gameState = GAME_STATE.WON;
        }
    }

    // setter to update game
    public GAME_STATE getGameState() {
        return this.gameState;
    }

    public void printDisplayBoard() {
        // print welcome message and board
        System.out.println();
        System.out.println("================== GAME STATS ================");
        System.out.printf("Moves to win: %d", numberOfMovesToWin);
        System.out.println();
        System.out.println();
        System.out.println("================== MY BOARD ==================\n");

        // board
        // number header
        System.out.print("   ");
        for (int i = 1; i <= boardSize; i++) {
            if (i < 10) {
                System.out.print("  " + i); // print the number columns with an extra space for single-digit numbers
            } else {
                System.out.print(" " + i); // print the number columns
            }
        }
        System.out.println();
        for (int i = 0; i < this.displayBoard.length; i++) {
            // letter column
            System.out.printf("%4c", (char) (i + 'a'));
            for (int j = 0; j < this.displayBoard[i].length; j++) {
                // squares
                System.out.printf("%2s", this.displayBoard[i][j]);
            }
            System.out.println();
        }

        if (totalSurroundingBombs > 0) {
            System.out.println();
            System.out.printf("There are %d bombs in the surrounding area.", totalSurroundingBombs);
            System.out.println();
        }
    }
}
