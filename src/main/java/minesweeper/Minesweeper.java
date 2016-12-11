package minesweeper;

//Beginner (8x8, 10 mines), Intermediate (16x16, 40 mines) and Expert (24x24, 99 mines)

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Minesweeper {
    // board[row][col]
    Cell[][] board;
    int numBombs;
    int numCellsUnseen;
    int size;
    boolean isActiveGame=true;

    public Minesweeper() {
        this(8,10);
    }

    public Minesweeper(int size, int numBombs) {
        this.size = size;
        board = new Cell[size][size];
        numCellsUnseen = size * size;
        this.numBombs = numBombs;
        initialize();
    }

    public Result guess(Coord c, Action action){
        //check bounds
        if (c.row < 0 || c.row >= size || c.col < 0 || c.col >= size){
            return Result.INVALID;
        }

        //mark if flag
        Cell cell = getCell(c);
        if (action == Action.FLAG){
            cell.hasFlag = true;
            return Result.PROCEED;
        }

        if (cell.hasBomb){
            cell.isSeen = true;
            cell.hasFlag = false;
            isActiveGame = false;
            return Result.LOSE;
        }

        if(cell.numBombs != 0){
            numCellsUnseen--;
            cell.isSeen = true;
            cell.hasFlag = false;
        }else if(cell.numBombs == 0){
            //if 0, must expand all zeros and all touching zero
            clearZeros(c);
        }

        if (numCellsUnseen == numBombs){
            isActiveGame = false;
            return Result.WIN;
        } else {
            return Result.PROCEED;
        }
    }

    public void clearZeros(Coord c){
        //starting from coord, add all around it that are 0 n/s/e/w to stack
        //process each by setting it to seen then repeating
        Stack<Coord> process = new Stack<Coord>();
        process.push(c);

        while(!process.empty()) {
            c = process.pop();
            Cell cur = getCell(c);

            if (cur.numBombs == 0) {//if valid one to expand
                cur.isSeen = true; //mark seen
                numCellsUnseen--;

                Coord test;
                //check all ones around it
                if (c.row > 0) { //not top
                    test = new Coord(c.row - 1, c.col);
                    Cell cand = getCell(test);
                    if (cand.numBombs == 0 && !cand.isSeen){
                        process.push(test);
                    }else if (cand.isSeen == false){
                        //mark the edges of the 0 circle seen
                        //by game setup, none can be bombs
                        cand.isSeen = true;
                        numCellsUnseen--;
                    }



                    if(c.col > 0) { //not top left
                        test = new Coord(c.row - 1, c.col-1);
                        cand = getCell(test);
                        if (cand.numBombs == 0 && !cand.isSeen){
                            process.push(test);
                        }else if (cand.isSeen == false){
                            cand.isSeen = true;
                            numCellsUnseen--;
                        }
                    }
                    if(c.col < size-1) { //not top right
                        test = new Coord(c.row - 1, c.col+1);
                        cand = getCell(test);
                        if (cand.numBombs == 0 && !cand.isSeen){
                            process.push(test);
                        }else if (cand.isSeen == false){
                            cand.isSeen = true;
                            numCellsUnseen--;
                        }
                    }


                }
                if (c.row < size - 1) { //not bottom
                    test = new Coord(c.row + 1, c.col);
                    Cell cand = getCell(test);
                    if (cand.numBombs == 0 && !cand.isSeen){
                        process.push(test);
                    }else if (cand.isSeen == false){
                        cand.isSeen = true;
                        numCellsUnseen--;
                    }


                    if(c.col > 0) { //not bottom left
                        test = new Coord(c.row+1, c.col-1);
                        cand = getCell(test);
                        if (cand.numBombs == 0 && !cand.isSeen){
                            process.push(test);
                        }else if (cand.isSeen == false){
                            cand.isSeen = true;
                            numCellsUnseen--;
                        }
                    }
                    if(c.col < size-1) { //not bottom right
                        test = new Coord(c.row+1, c.col+1);
                        cand = getCell(test);
                        if (cand.numBombs == 0 && !cand.isSeen){
                            process.push(test);
                        }else if (cand.isSeen == false){
                            cand.isSeen = true;
                            numCellsUnseen--;
                        }
                    }

                }
                if (c.col > 0) { //not left
                    test = new Coord(c.row, c.col - 1);
                    Cell cand = getCell(test);
                    if (cand.numBombs == 0 && !cand.isSeen){
                        process.push(test);
                    }else if (cand.isSeen == false){
                        cand.isSeen = true;
                        numCellsUnseen--;
                    }
                }
                if (c.col < size - 1) { //not right
                    test = new Coord(c.row, c.col + 1);
                    Cell cand = getCell(test);
                    if (cand.numBombs == 0 && !cand.isSeen){
                        process.push(test);
                    }else if (cand.isSeen == false){
                        cand.isSeen = true;
                        numCellsUnseen--;
                    }
                }
            }
        }
    }

    public void initialize(){
        // populate board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell();
            }
        }

        // create and add bombs
        for(int i = 0; i< numBombs; i++){
           Coord c = generateCoords(size);

           while (getCell(c).hasBomb){
               c = generateCoords(size);
           }
           addBombToBoard(c);
        }
    }

//    a b c
//    d e f
//    g h i

    void addBombToBoard(Coord c){
        Cell candidate = getCell(c.row, c.col);
        candidate.hasBomb=true;
        candidate.numBombs = -1; //indicate bomb
        Cell cur;

        if(c.row > 0){ //not top
            cur = board[c.row-1][c.col]; //b
            if(!cur.hasBomb) cur.numBombs++;

            if(c.col > 0) { //not top left
                cur = board[c.row-1][c.col-1]; //a
                if(!cur.hasBomb) cur.numBombs++;
            }
            if(c.col < size-1) { //not top right
                cur = board[c.row-1][c.col+1]; //c
                if(!cur.hasBomb) cur.numBombs++;
            }
        }
        if(c.row < size-1){ //not bottom
            cur = board[c.row+1][c.col]; //h
            if(!cur.hasBomb) cur.numBombs++;

            if(c.col > 0) { //not bottom left
                cur = board[c.row+1][c.col-1]; //g
                if(!cur.hasBomb) cur.numBombs++;
            }
            if(c.col < size-1) { //not bottom right
                cur = board[c.row+1][c.col+1]; //i
                if(!cur.hasBomb) cur.numBombs++;
            }
        }
        if(c.col > 0){ //not left
            cur = board[c.row][c.col-1]; //d
            if(!cur.hasBomb) cur.numBombs++;
        }
        if(c.col < size-1){ //not right
            cur = board[c.row][c.col+1]; //f
            if(!cur.hasBomb) cur.numBombs++;
        }

    }

    public Cell getCell(int row, int col){
        return board[row][col];
    }

    public Cell getCell(Coord c){
        return board[c.row][c.col];
    }

    Coord generateCoords(int size){
        Coord c = new Coord();
        Random rn = new Random();
        c.row = rn.nextInt(size);
        c.col = rn.nextInt(size);
        return c;
    }

    public void printBoardDebug(){
        System.out.println(Arrays.deepToString(board).replace("], ", "\n").replace(",", "").replace("[", "").replace("]]", ""));
    }

    public void printBoard(){
        for (int i = 0; i <= size+1; i++) {

            for (int j = 0; j <= size; j++) {
                //add numbers to lines
                if ( i == 0 && j == 0 ){
                    System.out.print("   ");
                    continue;
                }else if (i == 0) {
                    System.out.print((j-1) + " ");
                    continue;
                }else if(i == 1){
                    System.out.print("--");
                    continue;
                }else if (j == 0) {
                    System.out.print((i - 2) + "| ");
                    continue;
                }


               Cell c = getCell(i-2, j-1);
               if (c.hasFlag){
                   System.out.print("F ");
               }else if (c.isSeen && !c.hasBomb){
                   System.out.print( c.numBombs + " ");
               }else if (c.isSeen && c.hasBomb) {
                   System.out.print("B ");
               }else{
                   System.out.print("? ");
               }
            }
            System.out.println();
        }
    }

    public void printLoseMsg(){
        printBoard();
        System.out.println("\n" +
                "          ,--.!,\n" +
                "       __/   -*-\n" +
                "     ,d08b.  '|`\n" +
                "     0088MM     \n" +
                "     `9MMP'     \n" +
                "\n"+
                "You've hit a bomb! You lose. "
        );
    }

    public void printWinMsg(){
        printBoard();
        System.out.println("                                   .''.       \n" +
                "       .''.      .        *''*    :_\\/_:     . \n" +
                "      :_\\/_:   _\\(/_  .:.*_\\/_*   : /\\ :  .'.:.'.\n" +
                "  .''.: /\\ :   ./)\\   ':'* /\\ * :  '..'.  -=:o:=-\n" +
                " :_\\/_:'.:::.    ' *''*    * '.\\'/.' _\\(/_'.':'.'\n" +
                " : /\\ : :::::     *_\\/_*     -= o =-  /)\\    '  *\n" +
                "  '..'  ':::'     * /\\ *     .'/.\\'.   '\n" +
                "      *            *..*         :\n"+
                "You win! Congratulations!"
        );
    }
}