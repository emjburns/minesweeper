package minesweeper;

public class Coord {
    public int row;
    public int col;

    public Coord() {
    }

    public Coord(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "row: " + row + ", col: " + col;
    }
}
