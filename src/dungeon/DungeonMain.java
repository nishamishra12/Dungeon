package dungeon;

import java.util.Scanner;

import randomizer.ActualRandomizer;

/**
 * This a DungeonMain class which acts like a user input for the project.
 */
public class DungeonMain {

  /**
   * This is a main class which will be used to start the DungeonMain class.
   *
   * @param args Args can be provided as any
   */
  public static void main(String[] args) {

    Dungeon dungeon;
    try {
      if (args.length < 5) {
        throw new IllegalArgumentException("Invalid command line arguments given. "
                + "Please provide correct arguments");
      }
      int rows = Integer.parseInt(args[0]);
      int cols = Integer.parseInt(args[1]);
      int interConnectivity = Integer.parseInt(args[2]);
      int treasurePercent = Integer.parseInt(args[3]);
      boolean wrapping = Boolean.parseBoolean(args[4]);
      dungeon = new DungeonImpl(rows, cols, interConnectivity, treasurePercent, wrapping,
              new ActualRandomizer());
      System.out.println("*********** Dungeon Created ***************");
      System.out.println(dungeon.dumpDungeon());
      System.out.println("*************** Game Begins ***************");
      System.out.println("Start Cave: " + dungeon.getStartCave().getId());
      System.out.println("End Cave: " + dungeon.getEndCave().getId());
      Scanner sc = new Scanner(System.in);
      while (!dungeon.hasReachedEnd()) {
        System.out.println(dungeon.getPlayerDescription());
        if (dungeon.isTreasure()) {
          System.out.println(dungeon.getTreasureDescription());
          if (sc.next().equals("Y")) {
            dungeon.pickTreasure();
            System.out.println("Player successfully picked up the treasure");
          }
        }
        System.out.println(dungeon.getLocationDescription());
        System.out.println("Please select your next move");
        System.out.println("Enter N: North, Enter S: South, Enter E: East, Enter W: West");
        String input = sc.next();
        System.out.println(dungeon.nextMove(input));
        System.out.println("\n");
      }
      System.out.println("******** Player has reached the destination!! ******");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
}
