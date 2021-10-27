import java.util.Scanner;

import dungeon.Dungeon;
import dungeon.DungeonImpl;
import randomizer.ActualRandomizer;

public class driver {

  public static void main(String[] args) {

    Dungeon dungeon;
    dungeon = new DungeonImpl(3, 4, 2, 20, true
            , new ActualRandomizer());
    System.out.println("*********** Dungeon Created ***************");
    dungeon.dumpDungeon();
    Scanner sc = new Scanner(System.in);
    while (dungeon.tillEnd()) {
      System.out.println(dungeon.getLocationDescription());
      if (dungeon.isTreasure()) {
        System.out.println(dungeon.getTreasureDescription());
        if (sc.next().equals("Y")) {
          dungeon.pickTreasure();
          System.out.println("\n Player successfully picked up the treasure");
        }
      }
      System.out.println("\n Please select your next move");
      System.out.println("\n Enter N: North \n Enter S: South \n Enter E: East \n Enter W: West");
      String input = sc.next();
      dungeon.nextMove(input);
    }
  }
}
