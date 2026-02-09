import java.util.Scanner;
import enums.Symbol;
import entities.Board;
import entities.Player;
import entities.Position;
import entities.Game;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Create players
        Player playerX = new Player("Player X", Symbol.X);
        Player playerO = new Player("Player O", Symbol.O);

        // Create board and game
        Board board = new Board();
        Game game = new Game(board, playerX, playerO);

        Player currentPlayer = playerX;

        while (true) {
            printBoard(board);

            System.out.println(
                currentPlayer.getName() + 
                " (" + currentPlayer.getSymbol() + 
                "), enter row and column (0-2):"
            );

            int row = scanner.nextInt();
            int col = scanner.nextInt();

            try {
                board.makeMove(new Position(row, col), currentPlayer.getSymbol());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            // Check winner
            Symbol winner = game.checkWinner();
            if (winner != Symbol.EMPTY) {
                printBoard(board);
                System.out.println("Winner is: " + winner);
                break;
            }

            // Check draw
            if (game.isDraw()) {
                printBoard(board);
                System.out.println("Game is a draw.");
                break;
            }

            // Switch player
            currentPlayer =
                (currentPlayer == playerX) ? playerO : playerX;
        }

        scanner.close();
    }

    private static void printBoard(Board board) {
        System.out.println("Current Board:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Symbol s = board.getSymbolAt(new Position(i, j));
                System.out.print((s == Symbol.EMPTY ? "-" : s) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
