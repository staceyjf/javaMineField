import java.util.Random;
import java.util.Scanner;

public class Board {
    private Square[][] board;
    private int boardSize;
    private int amountOfBombsLeft;
    private Scanner scanner;

    public Board(int boardSize, Scanner scanner) {
        this.boardSize = boardSize;
        this.amountOfBombsLeft = 0;
        this.scanner = scanner;
        this.board = new Square[this.boardSize][this.boardSize]; // create the array

        // populate it with Squares
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                this.board[i][j] = new Square();
            }
        }
    }

    public void boardLogic() {
        // set the bombs
        setBombs();

        // print welcome message and board
        System.out.println();
        System.out.println("================== MY BOARD ==================");

        // board
        System.out.print("   ");
        for (int i = 97; i <= 97 + boardSize - 1; i++) {
            System.out.printf("%c  ", (char) i); // cast number to char
        }
        System.out.println();
        for (int i = 0; i < this.board.length; i++) {
            System.out.print((i + 1) + "  ");
            for (int j = 0; j < this.board[i].length; j++) {
                if (!this.board[i][j].getPlayState()) {
                    if (j <= 10) {
                        System.out.print("x ");
                    } else {
                        System.out.print("x   ");
                    }
                } else {
                    if (j <= 10) {
                        System.out.print("  ");
                    } else {
                        System.out.print("    ");
                    }
                }

            }
            System.out.println(); // print each row on a line new
        }
        System.out.println("\nEnter your co-ordinates: \n");
        // co-ords
        int coords = this.scanner.nextInt();
        scanner.nextLine();
        checkCoord(coords);
    }

    //
    public void checkCoord(int coords) {

    }

    // set the bombs
    public void setBombs() {
        int amountOfBombsSet = 0;
        int amountToSet = boardSize;

        while (amountOfBombsSet != amountToSet) {
            // set up random number generator
            Random random = new Random();
            int random_row = random.nextInt(board.length);
            int random_col = random.nextInt(board.length);
            // if the square does not have a bomb
            if (!board[random_row][random_col].getBombState()) {
                // set it as a bomb
                board[random_row][random_col].setBombState(true);
                // increase bomb count
                amountOfBombsSet++;
            }
        }
    }

    // get bomb count
    // to be used in Board stats
    public int bombCount() {
        this.amountOfBombsLeft = 0;
        // outter array
        for (int i = 0; i < board.length; i++) {
            // inner loop
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getBombState()) {
                    // incr bombCount
                    this.amountOfBombsLeft++;
                }
            }
        }
        return this.amountOfBombsLeft;
    }

}
