package randomizer;

import java.util.Random;

public class ActualRandomizer implements Randomizer {

  /**
   * {@inheritDoc}
   */
  @Override
  public int getNextInt(int min, int max) {
    Random rn = new Random();
    return rn.nextInt(max - min) + min;
  }
}
