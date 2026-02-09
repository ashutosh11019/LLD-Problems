package entities;

import enums.Symbol;

public class Board {
    private int rows;
    private int cols;
    private Symbol[][] grid;

    public Board(){
        rows = 3;
        cols = 3;
        grid = new Symbol[rows][cols];

        for(int i=0;i<rows;i++){
            for (int j=0;j<cols;j++) {
                grid[i][j]=Symbol.EMPTY;
            }
        }
    }

    public Symbol getSymbolAt(Position pos) {
        return grid[pos.getRow()][pos.getCol()];
    }

    public boolean isValidMove(Position pos){
        int i=pos.getRow(), j=pos.getCol();
        if(i>=0 && i<rows && j>=0 && j<cols && grid[i][j]==Symbol.EMPTY){
            return true;
        }
        return false;
    }

    public void makeMove(Position pos, Symbol symbol){
        if(isValidMove(pos)){
            int i=pos.getRow(), j=pos.getCol();
            grid[i][j] = symbol;
        }
    }

    public boolean isFull(){
        for(int i=0;i<rows;i++){
            for (int j=0;j<cols;j++) {
                if(grid[i][j]==Symbol.EMPTY){
                    return false;
                }
            }
        }
        return true;
    }
}
