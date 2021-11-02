package dungeontest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.Location;
import randomizer.ActualRandomizer;
import randomizer.Randomizer;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class to check all the implementation of the random class used to
 * generate random number values.
 */
public class ActualRandomizerTest {

  private Randomizer randomGenerator;
  private Dungeon dungeon;

  @Before
  public void setUp() throws Exception {
    randomGenerator = new ActualRandomizer();
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, randomGenerator);
  }

  @Test
  public void getNextInt() {
    assertTrue(2 <= randomGenerator.getNextInt(2, 4)
            && randomGenerator.getNextInt(2, 4) <= 4);
  }

  @Test
  public void shuffleList() {
    List<Location> list = new ArrayList<>(dungeon.getLocationList());
    //call shuffle method, the elements should get shuffled
    assertNotEquals(list, randomGenerator.shuffleList(list));
  }
}