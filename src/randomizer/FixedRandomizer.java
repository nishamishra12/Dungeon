package randomizer;

public class FixedRandomizer implements Randomizer {

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

}
