import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Board {
    private Square[][] board;
    private int boardSize;
    private int amountOfBomb;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        // bombs = same number as one side of the board
        this.amountOfBomb = this.boardSize;
        this.board = new Square[this.boardSize][this.boardSize];
    }

    public void printBoard() {
        System.out.println();
        System.out.println("====================== MY BOARD =============================");

        // print the header
        System.out.print("  ");
        int i;
        for (i = 97; i <= 97 + boardSize - 1; i++) {
            System.out.printf("%c  ", (char) i); // cast number to char
        }

        // print the board
        System.out.println();
        System.out.print("  ");
        Arrays.asList(this.board)
                .stream() // need to map inside
                .map((row) -> Arrays.stream(row).map((square) -> "x ").collect(Collectors.joining(" ")))
                .forEach((str) -> System.out.println(str));
    }

    // getter
    // don't combine getter & setters to adhere to encapsulation. Control the scope
    // of the function
    // (bind the data and the function)

}
