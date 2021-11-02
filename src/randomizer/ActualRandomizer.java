package randomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class represents the random generator class which implements all the methods
 * of the interface and is used to calculate random numbers.
 */
public class ActualRandomizer<T> implements Randomizer<T> {

  /**
   * {@inheritDoc}
   */
  @Override
  public int getNextInt(int min, int max) {
    Random rn = new Random();
    return rn.nextInt(max - min) + min;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<T> shuffleList(List<T> list) {
    List<T> newList = new ArrayList<>(list);
    Collections.shuffle(newList);
    return newList;
  }
}
