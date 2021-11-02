package dungeontest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeon.Cave;
import dungeon.Direction;
import dungeon.Location;
import dungeon.LocationType;
import dungeon.Treasure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test to check all the implementation of the cave class.
 */
public class CaveTest {

  private Location location;

  @Before
  public void setUp() {
    location = new Cave(1);
  }

  @Test
  public void getId() {
    assertEquals(1, location.getId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNegativeId() {
    new Cave(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullLocationConstruction() {
    new Cave(null);
  }

  @Test
  public void addNeighbors() {
    Map<Direction, Location> neighborList = new HashMap<Direction, Location>();
    neighborList.put(Direction.NORTH, location);
    location.addNeighbors(Direction.NORTH, location);
    assertEquals(neighborList, location.getNeighbors());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNeighborWrongDirection() {
    location.addNeighbors(null, location);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNeighborWrongLocation() {
    location.addNeighbors(Direction.EAST, null);
  }

  @Test
  public void getNeighbors() {
    location.addNeighbors(Direction.NORTH, location);
    assertEquals(1, location.getNeighbors().size());
  }

  @Test
  public void addTreasureList() {
    List<Treasure> treasureList = new ArrayList<Treasure>();
    treasureList.add(Treasure.SAPPHIRE);
    treasureList.add(Treasure.RUBY);
    treasureList.add(Treasure.DIAMOND);
    location.addTreasureList(Treasure.SAPPHIRE);
    location.addTreasureList(Treasure.RUBY);
    location.addTreasureList(Treasure.DIAMOND);
    assertEquals(treasureList, location.getTreasureList());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullTreasure() {
    location.addTreasureList(null);
  }

  @Test
  public void getTreasureList() {
    location.addTreasureList(Treasure.SAPPHIRE);
    location.addTreasureList(Treasure.RUBY);
    location.addTreasureList(Treasure.DIAMOND);
    location.addTreasureList(Treasure.DIAMOND);
    assertEquals(4, location.getTreasureList().size());
  }

  @Test
  public void setLocationType() {
    location.setLocationType(LocationType.TUNNEL);
    assertEquals(LocationType.TUNNEL, location.getLocationType());
    location.setLocationType(LocationType.CAVE);
    assertEquals(LocationType.CAVE, location.getLocationType());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullLocation() {
    location.setLocationType(null);
  }

  @Test
  public void getLocationType() {
    location.setLocationType(LocationType.TUNNEL);
    assertEquals(LocationType.TUNNEL, location.getLocationType());
    location.setLocationType(LocationType.CAVE);
    assertEquals(LocationType.CAVE, location.getLocationType());
  }

  @Test
  public void updateVisit() {
    location.updateVisit(false);
    assertFalse(location.isVisited());
    location.updateVisit(true);
    assertTrue(location.isVisited());
  }

  @Test
  public void isVisited() {
    location.updateVisit(false);
    assertFalse(location.isVisited());
    location.updateVisit(true);
    assertTrue(location.isVisited());
  }

  @Test
  public void removeTreasure() {
    location.addTreasureList(Treasure.SAPPHIRE);
    location.addTreasureList(Treasure.RUBY);
    location.addTreasureList(Treasure.DIAMOND);
    location.addTreasureList(Treasure.DIAMOND);
    assertEquals(4, location.getTreasureList().size());

    //now remove treasure
    location.removeTreasure();
    assertEquals(0, location.getTreasureList().size());
  }
}