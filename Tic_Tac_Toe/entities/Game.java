package entities;

import enums.Symbol;

public class Game {

    private final Board board;
    private final Player playerX;
    private final Player playerO;
    private Player currentPlayer;

    public Game(Board board, Player playerX, Player playerO) {
        this.board = board;
        this.playerX = playerX;
        this.playerO = playerO;
        this.currentPlayer = playerX;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

    public Symbol checkWinner() {
        // rows
        for (int i = 0; i < 3; i++) {
            Symbol s = board.getSymbolAt(new Position(i, 0));
            if (s != Symbol.EMPTY &&
                s == board.getSymbolAt(new Position(i, 1)) &&
                s == board.getSymbolAt(new Position(i, 2))) {
                return s;
            }
        }

        // columns
        for (int j = 0; j < 3; j++) {
            Symbol s = board.getSymbolAt(new Position(0, j));
            if (s != Symbol.EMPTY &&
                s == board.getSymbolAt(new Position(1, j)) &&
                s == board.getSymbolAt(new Position(2, j))) {
                return s;
            }
        }

        // diagonals
        Symbol center = board.getSymbolAt(new Position(1, 1));
        if (center != Symbol.EMPTY) {
            if (center == board.getSymbolAt(new Position(0, 0)) &&
                center == board.getSymbolAt(new Position(2, 2))) {
                return center;
            }
            if (center == board.getSymbolAt(new Position(0, 2)) &&
                center == board.getSymbolAt(new Position(2, 0))) {
                return center;
            }
        }

        return Symbol.EMPTY;
    }

    public boolean isDraw() {
        return board.isFull() && checkWinner() == Symbol.EMPTY;
    }

    public void announceResult() {
        Symbol winner = checkWinner();
        if (winner == Symbol.EMPTY) {
            System.out.println("Game is a draw");
        } else {
            System.out.println("Winner is: " + winner);
        }
    }
}
