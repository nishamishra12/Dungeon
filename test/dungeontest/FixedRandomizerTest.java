package dungeontest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.Location;
import randomizer.FixedRandomizer;
import randomizer.Randomizer;

import static org.junit.Assert.assertEquals;

/**
 * Test class to check all the implementation of the fixed random generator.
 */
public class FixedRandomizerTest {

  private Randomizer fixedRandGenerator;
  private Dungeon dungeon;

  @Before
  public void setUp() throws Exception {
    fixedRandGenerator = new FixedRandomizer(2);
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, new FixedRandomizer(2));
  }

  @Test
  public void getNextInt() {
    assertEquals(2, fixedRandGenerator.getNextInt(2, 4));
  }

  @Test
  public void shuffleList() {
    List<Location> list = new ArrayList<>(dungeon.getLocationList());
    //call shuffle method, the elements shouldn't get shuffled
    assertEquals(list, fixedRandGenerator.shuffleList(list));
  }
}