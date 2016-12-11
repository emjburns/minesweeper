package minesweeper;


import java.util.Scanner;

public class Main {
  public static void main(String[] args) {

	System.out.println(" #     #                                                                  \n" +
            " ##   ## # #    # ######  ####  #    # ###### ###### #####  ###### #####  \n" +
            " # # # # # ##   # #      #      #    # #      #      #    # #      #    # \n" +
            " #  #  # # # #  # #####   ####  #    # #####  #####  #    # #####  #    # \n" +
            " #     # # #  # # #           # # ## # #      #      #####  #      #####  \n" +
            " #     # # #   ## #      #    # ##  ## #      #      #      #      #   #  \n" +
            " #     # # #    # ######  ####  #    # ###### ###### #      ###### #    # \n" +
            "                                                                          \n");
      System.out.println("");

      Scanner sc = new Scanner(System.in);

      //Beginner (8x8, 10 mines), Intermediate (16x16, 40 mines) and Expert (24x24, 99 mines)

      Minesweeper minesweeper = new Minesweeper(8,10);

      System.out.println("Let's get started!");
      System.out.println("Instructions: ");
      System.out.println("To guess a coordinate, type the row, then the column:");
      System.out.println("0 3");
      System.out.println("To flag a cell, type 'F' then row and column");
      System.out.println("F 0 3");
      System.out.println("To see the board again, type 'P'");
      System.out.println("To quit, type 'Q'");
      System.out.println("");
      System.out.println("Guess away.....");
      minesweeper.printBoard();

      boolean playing = true;
      while (playing){
//          System.out.println("Guess: ");
          String input = sc.next();

          if (input.equals("Q") || input.equals("q")){
              playing = false;
              break;
          }
          if (input.equals("p")){
              //print debug board
              minesweeper.printBoardDebug();
              continue;
          }
          if (input.equals("P")){
              //print board
              minesweeper.printBoard();
              continue;
          }

          Action action = Action.GUESS;
          Coord coord = new Coord();


          try {
              if (input.equals("F") || input.equals("f")){
                action = Action.FLAG;
                coord.row = sc.nextInt();
                coord.col = sc.nextInt();
              }else {
                  coord.row = Integer.parseInt(input);
                  coord.col = sc.nextInt();
              }
          }catch (Exception e) {
              System.out.println("Invalid input received. Try again.");
              continue;
          }

          Result guessResult = minesweeper.guess(coord, action);

          if (guessResult == Result.LOSE){
              minesweeper.printLoseMsg();
              playing = false;
          }else if (guessResult == Result.WIN){
              minesweeper.printWinMsg();
              playing = false;
          }else{
              minesweeper.printBoard();
              System.out.println(guessResult);
          }
      }

      System.out.println("Goodbye. Play again soon!");
  }
}
