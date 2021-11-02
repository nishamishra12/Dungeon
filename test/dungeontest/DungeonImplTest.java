package dungeontest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.Location;
import dungeon.LocationType;
import dungeon.Treasure;
import randomizer.FixedRandomizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test to check all the implementation of the dungeon class and its mnethods.
 */
public class DungeonImplTest {

  private Dungeon dungeon;
  private Dungeon dungeonW;

  @Before
  public void setUp() {
    dungeon = new DungeonImpl(5, 7, 4, 20,
            false, new FixedRandomizer(2));
    dungeonW = new DungeonImpl(5, 7, 4, 20,
            true, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidRows() {
    new DungeonImpl(-1, 4, 2, 20,
            false, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidColumns() {
    new DungeonImpl(5, -1, 2, 20,
            false, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidInterConnectivity() {
    new DungeonImpl(5, 4, -1, 20,
            false, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void largeInterConnectivity() {
    new DungeonImpl(5, 4, 55, 20,
            false, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighTreasurePercent() {
    new DungeonImpl(5, 4, 55, 110,
            false, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowTreasurePercent() {
    new DungeonImpl(5, 4, 55, -10,
            false, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullRandomizer() {
    new DungeonImpl(5, 4, 55, 20,
            false, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void smallDungeon() {
    new DungeonImpl(2, 2, 1, 20,
            false, new FixedRandomizer(1));
  }

  @Test
  public void playerReachedEndCave() {
    dungeonW = new DungeonImpl(3, 4, 4, 20,
            true, new FixedRandomizer(2, 3, 4));

    assertEquals(2, dungeonW.getPlayer().getCurrentLocation().getId());
    dungeonW.nextMove("W");
    dungeonW.nextMove("W");
    dungeonW.nextMove("S");
    dungeonW.nextMove("W");
    dungeonW.nextMove("N");
    assertEquals(dungeonW.getEndCave().getId(), dungeonW.getPlayer().getCurrentLocation().getId());
    assertTrue(dungeonW.hasReachedEnd());
  }

  @Test
  public void testForTunnel2Entrance() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    //test for non wrapping dungeon
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.TUNNEL) {
        assertTrue(l.getNeighbors().size() == 2);
      }
    }

    dungeonW = new DungeonImpl(5, 4, 2, 20,
            true, new FixedRandomizer(2, 3, 4));
    //test for wrapping dungeon
    for (Location l : dungeonW.getLocationList()) {
      if (l.getLocationType() == LocationType.TUNNEL) {
        assertTrue(l.getNeighbors().size() == 2);
      }
    }
  }

  @Test
  public void testForCaveEntrance() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    //test for non wrapping dungeon
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        assertTrue(l.getNeighbors().size() == 1 || l.getNeighbors().size() == 3
                || l.getNeighbors().size() == 4);
      }
    }

    dungeonW = new DungeonImpl(5, 4, 2, 20,
            true, new FixedRandomizer(2, 3, 4));
    //test for wrapping dungeon
    for (Location l : dungeonW.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        assertTrue(l.getNeighbors().size() == 1 || l.getNeighbors().size() == 3
                || l.getNeighbors().size() == 4);
      }
    }
  }

  @Test
  public void testForConnectivityNonWrapping() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    for (Location l : dungeon.getLocationList()) {
      assertTrue(dungeon.bfs(l).size() == this.dungeon.getLocationList().size());
    }
  }

  @Test
  public void testForConnectivityWrapping() {
    dungeonW = new DungeonImpl(3, 4, 4, 20,
            true, new FixedRandomizer(2, 3, 4));
    for (Location l : dungeonW.getLocationList()) {
      assertTrue(dungeonW.bfs(l).size() == this.dungeonW.getLocationList().size());
    }
  }

  @Test
  public void testTreasurePercentNonWrapping() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    List<Location> onlyCaveList = new ArrayList<>();
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        onlyCaveList.add(l);
      }
    }
    //at least 20% of the caves will have treasure from the total cave list;
    int countOfCavesWithTreasure = 0;
    for (Location l : onlyCaveList) {
      if (l.getTreasureList().size() > 0) {
        countOfCavesWithTreasure++;
      }
    }
    assertTrue((countOfCavesWithTreasure * 100) / onlyCaveList.size() >= 20);
  }

  @Test
  public void testTreasurePercentWrapping() {
    dungeonW = new DungeonImpl(5, 4, 2, 20,
            true, new FixedRandomizer(2));
    List<Location> onlyCaveList = new ArrayList<>();
    for (Location l : dungeonW.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        onlyCaveList.add(l);
      }
    }
    //at least 20% of the caves will have treasure from the total cave list;
    int countOfCavesWithTreasure = 0;
    for (Location l : onlyCaveList) {
      if (l.getTreasureList().size() > 0) {
        countOfCavesWithTreasure++;
      }
    }
    assertTrue((countOfCavesWithTreasure * 100) / onlyCaveList.size() >= 20);
  }

  @Test
  public void testTreasurePercentExactNonWrapping() {
    dungeon = new DungeonImpl(5, 4, 1, 20,
            false, new FixedRandomizer(0));
    List<Location> onlyCaveList = new ArrayList<>();
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        onlyCaveList.add(l);
      }
    }
    //exact 20% of the caves will have treasure from the total cave list;
    int countOfCavesWithTreasure = 0;
    for (Location l : onlyCaveList) {
      if (l.getTreasureList().size() > 0) {
        countOfCavesWithTreasure++;
      }
    }
    assertEquals(20, (countOfCavesWithTreasure * 100) / onlyCaveList.size());
  }

  @Test
  public void testTreasurePercentExactWrapping() {
    dungeonW = new DungeonImpl(5, 4, 1, 20,
            true, new FixedRandomizer(0));
    List<Location> onlyCaveList = new ArrayList<>();
    for (Location l : dungeonW.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        onlyCaveList.add(l);
      }
    }
    //exact 20% of the caves will have treasure from the total cave list;
    int countOfCavesWithTreasure = 0;
    for (Location l : onlyCaveList) {
      if (l.getTreasureList().size() > 0) {
        countOfCavesWithTreasure++;
      }
    }
    assertEquals(20, (countOfCavesWithTreasure * 100) / onlyCaveList.size());
  }

  /**
   * Test to check if a cave can have more than one treasure.
   */
  @Test
  public void treasureInCave() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    List<Location> onlyCaveList = new ArrayList<>();
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        onlyCaveList.add(l);
      }
    }
    assertTrue(onlyCaveList.get(0).getTreasureList().size() > 1);

    //wrapping dungeon
    dungeonW = new DungeonImpl(5, 4, 1, 20,
            true, new FixedRandomizer(0));
    onlyCaveList = new ArrayList<>();
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        onlyCaveList.add(l);
      }
    }
    assertTrue(onlyCaveList.get(0).getTreasureList().size() > 1);
  }

  /**
   * Test to check that tunnels do not have treasures.
   */
  @Test
  public void tunnelNoTreasure() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    //non-wrapping dungeon
    for (Location l : dungeon.getLocationList()) {
      if (l.getLocationType() == LocationType.TUNNEL) {
        assertEquals(0, l.getTreasureList().size());
      }
    }

    //wrapping dungeon
    dungeonW = new DungeonImpl(5, 4, 1, 20,
            true, new FixedRandomizer(0));
    for (Location l : dungeonW.getLocationList()) {
      if (l.getLocationType() == LocationType.TUNNEL) {
        assertEquals(0, l.getTreasureList().size());
      }
    }
  }

  @Test
  public void nextMoveNonWrapping() {

    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    //check current cave
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());

    assertEquals(2, dungeon.getLocationList().get(1).getNeighbors().get(Direction.EAST).getId());
    assertEquals(5, dungeon.getLocationList().get(1).getNeighbors().get(Direction.SOUTH).getId());
    assertEquals(0, dungeon.getLocationList().get(1).getNeighbors().get(Direction.WEST).getId());

    dungeon.nextMove("S");
    assertEquals(5, dungeon.getPlayer().getCurrentLocation().getId());

    assertEquals(4, dungeon.getLocationList().get(5).getNeighbors().get(Direction.WEST).getId());
    assertEquals(6, dungeon.getLocationList().get(5).getNeighbors().get(Direction.EAST).getId());
    assertEquals(1, dungeon.getLocationList().get(5).getNeighbors().get(Direction.NORTH).getId());

    dungeon.nextMove("E");
    assertEquals(6, dungeon.getPlayer().getCurrentLocation().getId());

    assertEquals(2, dungeon.getLocationList().get(6).getNeighbors().get(Direction.NORTH).getId());
    assertEquals(7, dungeon.getLocationList().get(6).getNeighbors().get(Direction.EAST).getId());
    assertEquals(5, dungeon.getLocationList().get(6).getNeighbors().get(Direction.WEST).getId());

    dungeon.nextMove("N");
    assertEquals(2, dungeon.getPlayer().getCurrentLocation().getId());

    assertEquals(1, dungeon.getLocationList().get(2).getNeighbors().get(Direction.WEST).getId());
    assertEquals(6, dungeon.getLocationList().get(2).getNeighbors().get(Direction.SOUTH).getId());
    assertEquals(3, dungeon.getLocationList().get(2).getNeighbors().get(Direction.EAST).getId());

    dungeon.nextMove("W");
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
  }

  @Test
  public void nextMoveWrapping() {
    dungeonW = new DungeonImpl(3, 4, 4, 20,
            true, new FixedRandomizer(2, 3, 4));
    //check current cave
    assertEquals(2, dungeonW.getPlayer().getCurrentLocation().getId());

    assertEquals(1, dungeonW.getLocationList().get(2).getNeighbors().get(Direction.WEST).getId());

    //move to the west neighbor
    dungeonW.nextMove("W");
    assertEquals(1, dungeonW.getPlayer().getCurrentLocation().getId());

    assertEquals(0, dungeonW.getLocationList().get(1).getNeighbors().get(Direction.WEST).getId());
    assertEquals(2, dungeonW.getLocationList().get(1).getNeighbors().get(Direction.EAST).getId());
    assertEquals(9, dungeonW.getLocationList().get(1).getNeighbors().get(Direction.NORTH).getId());
    assertEquals(5, dungeonW.getLocationList().get(1).getNeighbors().get(Direction.SOUTH).getId());

    //move to the north neighbor, 1 - 9 (1 and 9 are wrapped), the player should go to 9
    dungeonW.nextMove("N");
    assertEquals(9, dungeonW.getPlayer().getCurrentLocation().getId());

    assertEquals(1, dungeonW.getLocationList().get(9).getNeighbors().get(Direction.SOUTH).getId());
    assertEquals(10, dungeonW.getLocationList().get(9).getNeighbors().get(Direction.EAST).getId());
    assertEquals(8, dungeonW.getLocationList().get(9).getNeighbors().get(Direction.WEST).getId());

    //move to the south neighbor, 1 - 9 (1 and 9 are wrapped), the player should go to 1
    dungeonW.nextMove("S");
    assertEquals(1, dungeonW.getPlayer().getCurrentLocation().getId());

    assertEquals(0, dungeonW.getLocationList().get(1).getNeighbors().get(Direction.WEST).getId());
    assertEquals(2, dungeonW.getLocationList().get(1).getNeighbors().get(Direction.EAST).getId());
    assertEquals(9, dungeonW.getLocationList().get(1).getNeighbors().get(Direction.NORTH).getId());
    assertEquals(5, dungeonW.getLocationList().get(1).getNeighbors().get(Direction.SOUTH).getId());

    dungeonW.nextMove("E");
    assertEquals(2, dungeonW.getPlayer().getCurrentLocation().getId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMove() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    //current location of the player - cave 1
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
    //enter an invalid direction
    dungeon.nextMove("T");
  }

  @Test
  public void wrongNeighborMove() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    //current location of the player - cave 1
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
    //enter an invalid direction
    assertEquals("This move is not possible, Enter another move", dungeon.nextMove("N"));
  }

  @Test
  public void dumpDungeonNonWrapping() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    assertEquals("\n" + "TUNNEL: 0\n" + "-->SOUTH Neighbor: CAVE: 4\n"
            + "-->EAST Neighbor: CAVE: 1\n" + "\n" + "CAVE: 1\n"
            + "-->SOUTH Neighbor: CAVE: 5\n" + "-->EAST Neighbor: CAVE: 2\n"
            + "-->WEST Neighbor: TUNNEL: 0\n" + "\n" + "CAVE: 2\n"
            + "-->SOUTH Neighbor: CAVE: 6\n" + "-->EAST Neighbor: CAVE: 3\n"
            + "-->WEST Neighbor: CAVE: 1\n" + "\n" + "CAVE: 3\n"
            + "-->WEST Neighbor: CAVE: 2\n" + "\n" + "CAVE: 4\n"
            + "-->NORTH Neighbor: TUNNEL: 0\n" + "-->SOUTH Neighbor: CAVE: 8\n"
            + "-->EAST Neighbor: CAVE: 5\n" + "\n" + "CAVE: 5\n"
            + "-->NORTH Neighbor: CAVE: 1\n" + "-->EAST Neighbor: CAVE: 6\n"
            + "-->WEST Neighbor: CAVE: 4\n" + "\n" + "CAVE: 6\n"
            + "-->NORTH Neighbor: CAVE: 2\n" + "-->EAST Neighbor: CAVE: 7\n"
            + "-->WEST Neighbor: CAVE: 5\n" + "\n" + "CAVE: 7\n"
            + "-->WEST Neighbor: CAVE: 6\n" + "\n" + "CAVE: 8\n"
            + "-->NORTH Neighbor: CAVE: 4\n" + "-->SOUTH Neighbor: CAVE: 12\n"
            + "-->EAST Neighbor: TUNNEL: 9\n" + "\n" + "TUNNEL: 9\n"
            + "-->EAST Neighbor: TUNNEL: 10\n" + "-->WEST Neighbor: CAVE: 8\n" + "\n"
            + "TUNNEL: 10\n" + "-->EAST Neighbor: CAVE: 11\n"
            + "-->WEST Neighbor: TUNNEL: 9\n" + "\n" + "CAVE: 11\n"
            + "-->WEST Neighbor: TUNNEL: 10\n" + "\n" + "CAVE: 12\n"
            + "-->NORTH Neighbor: CAVE: 8\n" + "-->SOUTH Neighbor: TUNNEL: 16\n"
            + "-->EAST Neighbor: TUNNEL: 13\n" + "\n" + "TUNNEL: 13\n"
            + "-->EAST Neighbor: TUNNEL: 14\n" + "-->WEST Neighbor: CAVE: 12\n" + "\n"
            + "TUNNEL: 14\n" + "-->EAST Neighbor: CAVE: 15\n"
            + "-->WEST Neighbor: TUNNEL: 13\n" + "\n" + "CAVE: 15\n"
            + "-->WEST Neighbor: TUNNEL: 14\n" + "\n" + "TUNNEL: 16\n"
            + "-->NORTH Neighbor: CAVE: 12\n" + "-->EAST Neighbor: TUNNEL: 17\n" + "\n"
            + "TUNNEL: 17\n" + "-->EAST Neighbor: TUNNEL: 18\n" + "-->WEST Neighbor: TUNNEL: 16\n"
            + "\n" + "TUNNEL: 18\n" + "-->EAST Neighbor: CAVE: 19\n"
            + "-->WEST Neighbor: TUNNEL: 17\n" + "\n"
            + "CAVE: 19\n" + "-->WEST Neighbor: TUNNEL: 18\n", dungeon.dumpDungeon());
  }

  @Test
  public void dumpDungeonWrapping() {
    dungeonW = new DungeonImpl(3, 4, 4, 20,
            true, new FixedRandomizer(2, 3, 4));
    assertEquals("\n" + "TUNNEL: 0\n"
            + "-->SOUTH Neighbor: CAVE: 4\n"
            + "-->EAST Neighbor: CAVE: 1\n" + "\n" + "CAVE: 1\n" + "-->NORTH Neighbor: CAVE: 9\n"
            + "-->SOUTH Neighbor: CAVE: 5\n" + "-->EAST Neighbor: CAVE: 2\n"
            + "-->WEST Neighbor: TUNNEL: 0\n"
            + "\n" + "CAVE: 2\n" + "-->WEST Neighbor: CAVE: 1\n" + "\n"
            + "CAVE: 3\n" + "-->SOUTH Neighbor: CAVE: 7\n" + "\n"
            + "CAVE: 4\n" + "-->NORTH Neighbor: TUNNEL: 0\n" + "-->SOUTH Neighbor: CAVE: 8\n"
            + "-->EAST Neighbor: CAVE: 5\n" + "-->WEST Neighbor: CAVE: 7\n" + "\n"
            + "CAVE: 5\n" + "-->NORTH Neighbor: CAVE: 1\n" + "-->EAST Neighbor: TUNNEL: 6\n"
            + "-->WEST Neighbor: CAVE: 4\n" + "\n"
            + "TUNNEL: 6\n" + "-->SOUTH Neighbor: TUNNEL: 10\n"
            + "-->WEST Neighbor: CAVE: 5\n" + "\n" + "CAVE: 7\n"
            + "-->NORTH Neighbor: CAVE: 3\n" + "-->SOUTH Neighbor: TUNNEL: 11\n"
            + "-->EAST Neighbor: CAVE: 4\n" + "\n" + "CAVE: 8\n"
            + "-->NORTH Neighbor: CAVE: 4\n" + "-->EAST Neighbor: CAVE: 9\n"
            + "-->WEST Neighbor: TUNNEL: 11\n" + "\n" + "CAVE: 9\n" + "-->SOUTH Neighbor: CAVE: 1\n"
            + "-->EAST Neighbor: TUNNEL: 10\n" + "-->WEST Neighbor: CAVE: 8\n" + "\n"
            + "TUNNEL: 10\n" + "-->NORTH Neighbor: TUNNEL: 6\n" + "-->WEST Neighbor: CAVE: 9\n"
            + "\n" + "TUNNEL: 11\n" + "-->NORTH Neighbor: CAVE: 7\n"
            + "-->EAST Neighbor: CAVE: 8\n", dungeonW.dumpDungeon());
  }

  @Test
  public void pickTreasure() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    //current cave location of the player has treasure and treasure list of player is empty
    assertEquals(3, dungeon.getPlayer().getCurrentLocation().getTreasureList().size());
    assertEquals(0, dungeon.getPlayer().getTreasureList().size());
    //pickup treasure
    dungeon.pickTreasure();
    //treasure emptied from the current cave and added to the player's treasure list
    assertEquals(0, dungeon.getPlayer().getCurrentLocation().getTreasureList().size());
    assertEquals(3, dungeon.getPlayer().getTreasureList().size());
    List<Treasure> tList = new ArrayList<>();
    tList.add(Treasure.SAPPHIRE);
    tList.add(Treasure.SAPPHIRE);
    tList.add(Treasure.SAPPHIRE);
    assertEquals(tList, dungeon.getPlayer().getTreasureList());
  }

  @Test
  public void getLocationDescriptionNonWrapping() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    assertEquals("\n" + "Next possible moves: \n" + "South\n" + "East\n"
            + "West", dungeon.getLocationDescription());
  }

  @Test
  public void getLocationDescriptionWrapping() {
    dungeonW = new DungeonImpl(3, 4, 4, 20,
            true, new FixedRandomizer(2, 3, 4));
    assertEquals("\n" + "Next possible moves: \n"
            + "West", dungeonW.getLocationDescription());
  }

  @Test
  public void getTreasureDescriptionNonWrapping() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    assertEquals("\n" + "Treasure found in current cave.\n"
                    + "Sapphire\n" + "Sapphire\n" + "Sapphire\n"
                    + "There is treasure in the cave. Do you want to pick it up? (Y/N)",
            dungeon.getTreasureDescription());
  }

  @Test
  public void getTreasureDescriptionWrapping() {
    dungeonW = new DungeonImpl(3, 4, 4, 20,
            true, new FixedRandomizer(2, 3, 4));
    assertEquals("\n" +
                    "Treasure found in current cave.\n"
                    + "Ruby\n" + "Diamond\n" + "Sapphire\n" + "Ruby\n"
                    + "There is treasure in the cave. Do you want to pick it up? (Y/N)",
            dungeonW.getTreasureDescription());
  }

  @Test
  public void getPlayerDescriptionNonWrapping() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    assertEquals("The player is in CAVE: 1\n"
            + "Player has no treasure", dungeon.getPlayerDescription());
    dungeon.pickTreasure();
    assertEquals("The player is in CAVE: 1\n"
            + "Player has following treasures: SAPPHIRE: 3", dungeon.getPlayerDescription());
  }

  @Test
  public void getPlayerDescriptionWrapping() {
    dungeonW = new DungeonImpl(3, 4, 4, 20,
            true, new FixedRandomizer(2, 3, 4));
    assertEquals("The player is in CAVE: 2\n"
            + "Player has no treasure", dungeonW.getPlayerDescription());
    dungeonW.pickTreasure();
    assertEquals("The player is in CAVE: 2\n"
                    + "Player has following treasures: DIAMOND: 1 RUBY: 2 SAPPHIRE: 1",
            dungeonW.getPlayerDescription());
  }

  @Test
  public void isTreasure() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    //check if the current cave the player is in has treasure
    assertEquals(3, dungeon.getPlayer().getCurrentLocation().getTreasureList().size());
    //since the current cave has treasure the method should return true
    assertEquals(true, dungeon.isTreasure());

    //pickup treasure from the current cave
    dungeon.pickTreasure();
    //now since the treasure is removed from the current cave, the method should return false
    assertEquals(false, dungeon.isTreasure());
  }

  @Test
  public void getLocationList() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    dungeonW = new DungeonImpl(3, 4, 4, 20,
            true, new FixedRandomizer(2, 3, 4));
    //get list of all locations in the dungeon
    assertEquals(20, dungeon.getLocationList().size());
    assertEquals(12, dungeonW.getLocationList().size());
  }

  @Test
  public void getPlayer() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    assertEquals("John", dungeon.getPlayer().getName());
  }

  @Test
  public void getStartCave() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    assertEquals(1, dungeon.getStartCave().getId());
  }

  @Test
  public void getEndCave() {
    dungeon = new DungeonImpl(3, 4, 2, 20,
            true, new FixedRandomizer(0));
    assertEquals(11, dungeon.getEndCave().getId());
  }

  @Test
  public void startEndAreCave() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    assertEquals(LocationType.CAVE, dungeon.getStartCave().getLocationType());
    assertEquals(LocationType.CAVE, dungeon.getEndCave().getLocationType());
  }

  @Test
  public void testStartEndMinimumFive() {

    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));

    Map<Location, Integer> bfsLevel = dungeon.bfs(dungeon.getStartCave());

    int distance = 0;
    for (Map.Entry<Location, Integer> levels : bfsLevel.entrySet()) {
      if (dungeon.getEndCave().getId() == levels.getKey().getId()) {
        distance = levels.getValue();
      }
    }
    assertTrue(distance >= 5);
  }

  @Test
  public void playerStartsAtStart() {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
    assertEquals(dungeon.getStartCave().getId(), dungeon.getPlayer().getCurrentLocation().getId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForBFSNullStart() {
    dungeon.bfs(null);
  }
}