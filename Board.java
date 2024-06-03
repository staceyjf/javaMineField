
import java.util.Random;
import java.util.Arrays;
import java.util.List;

public class Board {
    private int[][] logicBoard;
    private String[][] displayBoard;
    private int boardSize;
    private int totalBombs;
    private int totalSurroundingBombs;
    private int numberOfMovesToWin;

    private GAME_STATE gameState; // controls the game state to update Game

    public Board(int boardSize, int totalBombs) {
        this.boardSize = boardSize; // user defined
        this.totalBombs = totalBombs; // user defined
        this.totalSurroundingBombs = 0;
        this.numberOfMovesToWin = ((boardSize * boardSize) - totalBombs);
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

    public void boardSetUp() {
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

        // TODO: calculate the bombs here ahead of time
    }

    // this check if its a flag, the game is won, lost or in progress
    public void handleMove(int y_coord, int x_coord, int user_action) {
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

    // getters
    public GAME_STATE getGameState() {
        return this.gameState;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int[][] getLogicBoard() {
        return logicBoard;
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
