package dungeon;

/**
 * This is an enumeration for the four types of direction a player can move in the dungeon.
 */
public enum Direction {

  NORTH("North"),
  SOUTH("South"),
  EAST("East"),
  WEST("West");

  private final String direction;

  Direction(String direction) {
    this.direction = direction;
  }

  public String getDirection() {
    return direction;
  }
}
