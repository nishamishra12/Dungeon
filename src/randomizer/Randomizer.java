package randomizer;

import java.util.List;

/**
 * This interface represents generation of actual and fixed random numbers.
 */
public interface Randomizer<T> {

  /**
   * This method provides a random number between minimum and maximum values.
   *
   * @param min this parameter takes the minimum value
   * @param max this parameter takes the maximum value
   * @return an integer between minimum and maximum value
   */
  public int getNextInt(int min, int max);

  /**
   * This method provides a shuffled list of equipment based on whether the random or fixed
   * randomizer class is called.
   *
   * @param list this parameter takes the not shuffled array list
   */
  public List<T> shuffleList(List<T> list);
}
