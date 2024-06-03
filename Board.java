
import java.util.Random;
import java.util.Arrays;
import java.util.List;

// 0 unplayed
// 9 bomb
// 10 played

public class Board {
    private int[][] logicBoard;
    private String[][] displayBoard;
    private int boardSize;
    private int totalBombs;
    private int numberOfMovesToWin;

    private GAME_STATE gameState; // controls the game state to update Game

    public Board(int boardSize, int totalBombs) {
        this.boardSize = boardSize; // user defined
        this.totalBombs = totalBombs; // user defined
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

        // update the logic board with adjacent bomb count
        for (int i = 0; i < this.logicBoard.length; i++) {
            for (int j = 0; j < this.logicBoard[i].length; j++) {
                // if the square is a bomb, set the sounding squares with a bomb count
                if (logicBoard[i][j] == 9) {
                    for (int[] transformation : transformations) {
                        int newX = j + transformation[1]; // the column
                        int newY = i + transformation[0]; // the row

                        // update the bomb count
                        // validate that the position is within the board
                        if (newX >= 0 && newX < logicBoard[0].length && newY >= 0 && newY < logicBoard.length) {
                            if (logicBoard[newY][newX] != 9) {
                                logicBoard[newY][newX]++; // increase to reflect bomb count
                            }
                        }
                    }
                }
            }
        }

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
            default: // if unplayed - check if game is won or game must continue
                cascadeSquares(y_coord, x_coord);
                // I've already handled case 1 in the while loop in checkCoords
        }

        // check to see if game is won
        if (numberOfMovesToWin == 0)

        {
            this.gameState = GAME_STATE.WON;
        }
    }

    // handle the cascading squares
    public void cascadeSquares(int y_coord, int x_coord) {

        // create an exit point for the recursion eg if the square is a bomb (9) or been
        // played (1) or has a flag eg it will go back to asking for valid coords (game
        // logic)
        if (this.logicBoard[y_coord][x_coord] == 9 || this.logicBoard[y_coord][x_coord] == 10
                || this.displayBoard[y_coord][x_coord].equals(SQUARE.FLAG.getDisplayValue())) {
            return;
        }

        if (this.logicBoard[y_coord][x_coord] > 0 && this.logicBoard[y_coord][x_coord] < 9) {
            this.numberOfMovesToWin--; // reduce the number of moves left
            // update the square with the bomb count
            this.displayBoard[y_coord][x_coord] = String.valueOf(this.logicBoard[y_coord][x_coord]);
            this.logicBoard[y_coord][x_coord] = 10; // update to played
            return;
        }

        // update to played if there is no bomb count
        this.logicBoard[y_coord][x_coord] = 10; // updated to played
        this.displayBoard[y_coord][x_coord] = SQUARE.PLAYED.getDisplayValue(); // updated the display board with
        this.numberOfMovesToWin--; // reduce the number of moves left

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

        // call the next square if the position is within the board
        for (int[] transformation : transformations) {
            int newX = x_coord + transformation[1];
            int newY = y_coord + transformation[0];

            if (newY >= 0 && newY < logicBoard.length && newX >= 0 && newX < logicBoard[0].length) {
                cascadeSquares(newY, newX);
            }

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

    public String[][] getDisplayBoard() {
        return displayBoard;
    }

    public int getNumberOfMovesToWin() {
        return numberOfMovesToWin;
    }

    // only for debugging
    public void printLogicBoard() {
        System.out.println(Arrays.deepToString(this.logicBoard));
    }
}
