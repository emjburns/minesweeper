package minesweeper;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

      //
      System.out.println("Let's get started!");
      System.out.println("What level would you like to play?");
      System.out.println("Easy (8x8, 10 mines), Medium (16x16, 40 mines) or Hard (24x24, 99 mines)?");
      System.out.println("Type 'E' for easy, 'M' for medium, and 'H' for hard");
      String in = sc.next();
      List<String> validInput = new ArrayList<String >(Arrays.asList("E", "e", "M", "m", "H", "h"));
      while (!validInput.contains(in)){
          System.out.println("Invalid level selection. Please choose 'E', 'M', or 'H'.");
          in = sc.next();
      }

      int size = 1;
      int numBombs = 1;

      if (in.equals("E") || in.equals("e")){
          size = 8;
          numBombs = 10;
      }else if (in.equals("M") || in.equals("m")){
          size = 16;
          numBombs = 40;
      }else if (in.equals("H") || in.equals("h")){
          size = 24;
          numBombs = 99;
      }

      Minesweeper minesweeper = new Minesweeper(size, numBombs);

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
          String input = sc.nextLine();
          String splitinput[] = input.split(" ");

          if (splitinput.length == 1 && (splitinput[0].equals("Q") || splitinput[0].equals("q"))){
              playing = false;
              break;
          }
          if (splitinput.length == 1 &&  splitinput[0].equals("p")){
              //print debug board
              minesweeper.printBoardDebug();
              continue;
          }
          if (splitinput.length == 1 && splitinput[0].equals("P")){
              //print board
              minesweeper.printBoard();
              continue;
          }

          Action action = Action.GUESS;
          Coord coord = new Coord();


          try {
              if (splitinput[0].equals("F") || splitinput[0].equals("f")){
                action = Action.FLAG;
                coord.row = Integer.parseInt(splitinput[1]);
                coord.col = Integer.parseInt(splitinput[2]);
              }else {
                  coord.row = Integer.parseInt(splitinput[0]);
                  coord.col = Integer.parseInt(splitinput[1]);
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
