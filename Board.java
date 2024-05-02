
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

// ENUM
// 0 is unplayed / "\uD83C\uDF54"; // hamburger emoji
// 1 is played / "  "
// 9 is bomb "U+1F4A3"

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
        this.totalBombs = 0;
        this.numberOfMovesToWin = ((boardSize * boardSize) - totalBombs);
        this.scanner = scanner;
        this.logicBoard = new int[boardSize][boardSize]; // initialise to zero as default
        this.displayBoard = new String[boardSize][boardSize];
        this.gameState = GAME_STATE.IN_PROGRESS;

        // initialise board with unplayed squares
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
        System.out.println("\nEnter your co-ordinates (eg a1):");

        // co-ords

        int y_coord = 0;
        int x_coord = 0;

        // check until valid co-ords
        while (true) {
            String userCoords = this.scanner.nextLine(); // ask for new input each time

            try {
                // if empty
                if (userCoords.isEmpty()) {
                    throw new IllegalArgumentException("Oops - please try again and enter co-ordinates");
                }

                // transform "a1" -> split into y,x
                // column = letter -> to int - 1 (inner) -> y coords
                // row = int -> -1 for index (outter ) -> x coords
                char columnChar = userCoords.charAt(0); // 'a'
                String rowString = userCoords.substring(1); // '1'

                // if not right type for y
                if (!Character.isLetter(columnChar)) {
                    throw new IllegalArgumentException("Oops - please try again as the y co-ordinate was not a letter");
                }

                // if not the right type for x
                try {
                    Integer.parseInt(rowString); // will throw an exception if it can't convert
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Oops - please try again as the x co-ordinate was not a number");
                }

                y_coord = columnChar - 'a'; // subtract "1 the value of a (the unicode value) to get the right index
                x_coord = Integer.parseInt(rowString) - 1; // to get the right index

                // if not inside the board
                if (x_coord < 0 || x_coord >= boardSize || y_coord < 0
                        || y_coord >= boardSize) {
                    throw new IllegalArgumentException(
                            "Oops - please try again as the co-ordinates where outside of the board range");
                }

                // if played eg 1
                if (logicBoard[y_coord][x_coord] == 1) {
                    throw new SquareAlreadyPlayedException("Oops - that square has already been played");
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

        // check to see if game is won
        if (numberOfMovesToWin == 0) {
            this.gameState = GAME_STATE.WON;
        }

        // check to see if game is lost or sqaure is unplayed
        switch (logicBoard[y_coord][x_coord]) {
            case 9: // if a bomb - end game
                this.displayBoard[y_coord][x_coord] = SQUARE.BOMB.getDisplayValue(); // updated the display board
                System.out.println("You hit a bomb");
                // how would i update the player's stats if connected board
                this.gameState = GAME_STATE.LOST;
                break;
            case 0: // if unplayed
                this.logicBoard[y_coord][x_coord] = 1; // updated to play
                this.displayBoard[y_coord][x_coord] = SQUARE.PLAYED.getDisplayValue(); // updated the display board with
                numberOfMovesToWin--; // reduce the number of moves left

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

                for (int[] transformation : transformations) {
                    int newY = y_coord + transformation[0];
                    int newX = x_coord + transformation[1];

                    /// ensure co-ords are valid
                    if (newY >= 0 && newY < logicBoard.length && newX >= 0 && newX < logicBoard[0].length) {
                        if (this.logicBoard[newY][newX] == 9) {
                            totalSurroundingBombs++; // increase bomb count for surrounding bombs
                        }
                    }
                }
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
        // letter header
        System.out.print("  ");
        for (int i = 97; i <= 97 + boardSize - 1; i++) {
            System.out.printf("%3c", (char) i); // print the letter columns
        }
        System.out.println();
        for (int i = 0; i < this.displayBoard.length; i++) {
            // number column
            System.out.printf("%3d", (i + 1));
            for (int j = 0; j < this.displayBoard[i].length; j++) {
                // squares
                System.out.printf("%2s", this.displayBoard[i][j]);
            }
            System.out.println();
        }
    }
}
