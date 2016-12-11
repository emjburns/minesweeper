package minesweeper;

public class Cell {
    public boolean hasBomb = false;
    public boolean hasFlag = false;
    public boolean isSeen = false;
    public int numBombs = 0; //-1 if bomb

    @Override
    public String toString() {
        return (hasBomb? "b" : Integer.toString(numBombs));


//        return "[" +
//                "b=" + hasBomb +
//                ", f=" + hasFlag +
//                ", s=" + isSeen +
//                ", nb=" + numBombs +
//                ']';
    }
}
