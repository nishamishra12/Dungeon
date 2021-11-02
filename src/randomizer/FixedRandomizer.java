package randomizer;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the fixed random generator class which implements all the methods
 * of the interface and is used to calculate fixed random numbers.
 */
public class FixedRandomizer<T> implements Randomizer<T> {

  private final int[] arr;
  private int index;

  /**
   * Constructs a fixed random generator by taking varargs as the input.
   *
   * @param arr this parameter takes varargs as integers
   */
  public FixedRandomizer(int... arr) {
    this.arr = arr;
    this.index = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getNextInt(int min, int max) {
    int val = arr[index++];
    index = index >= arr.length ? 0 : index;
    return val;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<T> shuffleList(List<T> list) {
    return new ArrayList<T>(list);
  }

}
