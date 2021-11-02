package dungeontest;

import org.junit.Before;
import org.junit.Test;

import dungeon.Cave;
import dungeon.Direction;
import dungeon.Location;
import dungeon.Player;
import dungeon.PlayerImpl;
import dungeon.Treasure;

import static org.junit.Assert.assertEquals;

/**
 * Test class to check all the implementation of the Player class.
 */
public class PlayerImplTest {

  private Player player;
  private Location startCave;

  @Before
  public void setUp() throws Exception {
    startCave = new Cave(1);
    player = new PlayerImpl("Jack", startCave);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullName() {
    new PlayerImpl(null, startCave);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullCave() {
    new PlayerImpl("Jack", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullPlayer() {
    new PlayerImpl(null);
  }

  @Test
  public void getName() {
    assertEquals("Jack", player.getName());
  }

  @Test
  public void getCurrentLocation() {
    //current location and start cave is same as player hasn't moved anywhere
    assertEquals(startCave, player.getCurrentLocation());
  }

  @Test
  public void updateTreasureList() {
    startCave.addTreasureList(Treasure.RUBY);
    assertEquals(1, startCave.getTreasureList().size());

    //call update treasure
    player.updateTreasureList();
    assertEquals(1, player.getTreasureList().size());
    assertEquals(0, startCave.getTreasureList().size());
  }

  @Test
  public void getTreasureList() {
    startCave.addTreasureList(Treasure.RUBY);
    player.updateTreasureList();
    assertEquals(1, player.getTreasureList().size());
  }

  @Test
  public void move() {
    startCave.addNeighbors(Direction.EAST, new Cave(2));
    startCave.addNeighbors(Direction.WEST, new Cave(6));
    //move to one of the neighbors
    player.move(Direction.EAST);
    //assert the current cave id after player movement
    assertEquals(2, player.getCurrentLocation().getId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullMove() {
    player.move(null);
  }
}