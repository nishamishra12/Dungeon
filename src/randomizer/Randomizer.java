package randomizer;

/**
 * This interface represents generation of actual and fixed random numbers.
 */
public interface Randomizer {

  /**
   * This method provides a random number between minimum and maximum values.
   *
   * @param min this parameter takes the minimum value
   * @param max this parameter takes the maximum value
   * @return an integer between minimum and maximum value
   */
  public int getNextInt(int min, int max);
}
