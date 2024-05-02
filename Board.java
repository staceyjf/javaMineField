import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

// ENUM
// 0 is unplayed / "\uD83C\uDF54"; // hamburger emoji
// 1 is played / "  "
// 9 is bomb "U+1F4A3"

public class Board {
    private int[][] logicBoard;
    private String[][] displayBoard;
    private int boardSize;
    private int amountOfBombs;
    private int amountOfBombsLeft;
    private int numberOfMoves;
    private Scanner scanner;

    public Board(int boardSize, int amountOfBombs, Scanner scanner) {
        this.boardSize = boardSize; // user defined
        this.amountOfBombs = amountOfBombs; // user define
        this.amountOfBombsLeft = 0;
        this.numberOfMoves = 0;
        this.scanner = scanner;
        this.logicBoard = new int[boardSize][boardSize]; // initialise to zero as default
        this.displayBoard = new String[boardSize][boardSize];

        // initialise board with ENUM unplayed
        for (int i = 0; i < displayBoard.length; i++) {
            for (int j = 0; j < displayBoard[i].length; j++) {
                this.displayBoard[i][j] = SQUARE.UNPLAYED.getDisplayValue();
            }
        }
    }

    public void boardLogic() {
        // set the bombs
        int amountOfBombsSet = 0;
        Random random = new Random();

        // plant the bombs
        while (amountOfBombsSet != amountOfBombs) {
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
        handleCoord(); // handles user co-ords inputs
    }

    //
    public void handleCoord() {
        // make this a loop until valid co-ords are entered
        System.out.println(); // print each row on a line new
        System.out.println("\nEnter your co-ordinates (eg a1): \n");

        // co-ords
        String userCoords = this.scanner.nextLine();
        int y_coord = 0;
        int x_coord = 0;

        // check until valid co-ords
        while (true) {
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
                    Integer.parseInt(rowString);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Oops - please try again as the x co-ordinate was not a number");
                }

                y_coord = columnChar - 'a';
                // subtract "1 the value of a (the unicode value) to get the right index
                x_coord = Integer.parseInt(rowString) - 1; // to get the right index

                // if not inside the board
                if (x_coord < 0 || x_coord >= boardSize || y_coord < 0
                        || y_coord >= boardSize) {
                    throw new IllegalArgumentException(
                            "Oops - please try again as the co-ordinates where outside of the board range");
                }

                break; // to exit out when co-ords are valid
            } catch (NumberFormatException ex) {
                System.out.println("Invalid co-ordinate - please input a co-ord like 'a1'");
            } catch (StringIndexOutOfBoundsException ex) {
                System.out.println("Invalid co-ordinate - please input a co-ord within the board range");
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }

        System.out.println(y_coord);
        System.out.println(x_coord);

        // check logicbaord
        // if logicBoard[row][column] == 9 -> you lose
        // updatedate displaybaord

        // checkWin
        // endgame etc

        // if logicBoard[row][column] != 9 && logicBoard[row][column] != 1 ->
        // update displayboard
        // find bombs nearby -
        // update number of moves++
        // update current square to bombs nearby for display
        // emoji number U+0030 U+20E3
        // call print

        // restart
        // display board
        // call handleCoord
    }

    // update displayBoard
    public void updateDisplayBoard() {
        for (int i = 0; i < this.displayBoard.length; i++) {
            for (int j = 0; j < this.displayBoard[i].length; j++) {
                switch (logicBoard[i][j]) {
                    case 9:
                        this.displayBoard[i][j] = SQUARE.BOMB.getDisplayValue();
                        break;
                    case 1:
                        this.displayBoard[i][j] = SQUARE.PLAYED.getDisplayValue();
                        break;
                    case 0:
                        this.displayBoard[i][j] = SQUARE.UNPLAYED.getDisplayValue();
                        break;
                }
            }
        }
    }

    public void printDisplayBoard() {
        // print welcome message and board
        System.out.println();
        System.out.println("================== MY BOARD ==================");

        // board
        // letter header
        System.out.print("   ");
        for (int i = 97; i <= 97 + boardSize - 1; i++) {
            System.out.printf("%2c", (char) i); // print the letter columns
        }
        System.out.println();
        for (int i = 0; i < this.displayBoard.length; i++) {
            // number column
            System.out.printf("%3d", (i + 1));
            for (int j = 0; j < this.displayBoard[i].length; j++) {
                // sqaures
                System.out.printf("%2s", this.displayBoard[i][j]);
            }
            System.out.println();
        }
    }

    // get bomb count
    // to be used in Board stats
    public int bombCount() {
        this.amountOfBombsLeft = 0;
        // outter array
        for (int i = 0; i < logicBoard.length; i++) {
            // inner loop
            for (int j = 0; j < logicBoard[i].length; j++) {
                if (logicBoard[i][j] == 9) { // 9 is our bomb
                    // incr bombCount
                    this.amountOfBombsLeft++;
                }
            }
        }
        return this.amountOfBombsLeft;
    }
}

//
// Board
// -Underlying data (int)
// [0,1,9,1,0][0,1,1,1,0]
// -Displayed data (string)
// ['0','1',' ',' ',' ']['0','1','1',' ',' ']

// ->

// user input
// (1, 1)
// (2, 1)
// (0, 0)

// if (displayedData[1][1] != ' ') {
// throw new Error("You have already inputed these coords")
// }

// if the cell hasn't been played updated the underlyingData
// displayedData[1][1] = underlyingData[1][1]

// [0, 0, 9, 0, 0]
// [0, 9, 2, 0, 0]

// list of tranformations
// (2, 1)
// -> (x, y) -> (dx, dy)
// -> (1, 0) -> (-1, -1)
// -> (2, 0) -> ( 0, -1)
// -> (3, 0) -> ( 1, -1)
// -> (1, 1) -> (-1, 0)
// -> (3, 1) -> ( 1, 0)
// -> (1, 2) -> (-1, 1)
// -> (2, 2) -> ( 0, 1)
// -> (3, 2) -> ( 1, 1)

// (0,0)
// for (transformations) {
// try {
// // (-1, -1)
// } catch (e) {
// continue;
// }
// }